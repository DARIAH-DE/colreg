package eu.dariah.de.colreg.migration;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import de.unibamberg.minf.dme.model.version.VersionInfo;
import de.unibamberg.minf.dme.model.version.VersionInfoImpl;
import eu.dariah.de.colreg.dao.VersionDao;
import eu.dariah.de.colreg.dao.base.DaoImpl;
import eu.dariah.de.colreg.dao.vocabulary.generic.VocabularyDao;
import eu.dariah.de.colreg.dao.vocabulary.generic.VocabularyItemDao;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.vocabulary.generic.Vocabulary;
import eu.dariah.de.colreg.model.vocabulary.generic.VocabularyItem;

@Component
public class MigrationServiceImpl implements MigrationService {
	private final static Logger logger = LoggerFactory.getLogger(MigrationServiceImpl.class);
	private final static String versionHashPrefix = "CollectionRegistry";

	private static final Pattern LOCALIZABLE_ENTITY_IDENTIFIER_PATTERN = Pattern.compile("([A-Za-z0-9-_])+");
	
	@Value(value="${paths.backups}")
	private String backupsBasePath;
	
	@Value(value="${mongo.database}")
	private String database;
	
	private final MessageDigest md;
	
	@Autowired private MongoTemplate mongoTemplate;
	@Autowired private ObjectMapper objectMapper;
	
	@Autowired private VersionDao versionDao;
	@Autowired private VocabularyDao vocabularyDao;
	@Autowired private VocabularyItemDao vocabularyItemDao;
	
	public MigrationServiceImpl() throws NoSuchAlgorithmException {
		md = MessageDigest.getInstance("MD5");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		List<String> versions = new ArrayList<String>();
		List<VersionInfo> versionInfos = versionDao.findAll();
		for (VersionInfo vi : versionInfos) {
			if (!vi.getVersionHash().equals(new String(md.digest(new String(versionHashPrefix + vi.getVersion()).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8))) {
				logger.error("Cancelling migration checks: failed to compare version hashes. Is the correct database configured?");
				return;
			}
			versions.add(vi.getVersion());
		}
		this.performMigrations(versions);
	}
	
	private void performMigrations(List<String> existingVersions) throws Exception {
		boolean backedUp = false;
		
		if (!existingVersions.contains("3.8")) {
			if (!backedUp) {
				this.backupDb();
				backedUp = true;
			}
			this.migrateImages();
		}
		if (!existingVersions.contains("3.8.1")) {
			if (!backedUp) {
				this.backupDb();
				backedUp = true;
			}
			this.migrateCollectionTypesVocabulary();
		}
		if (!existingVersions.contains("3.8.2")) {
			if (!backedUp) {
				this.backupDb();
				backedUp = true;
			}
			this.migrateCollectionTypes();
		}
	}
	
	private void migrateCollectionTypesVocabulary() {
		logger.info("Performing collection types vocabulary migration (version: 3.8.1)");
		
		List<Vocabulary> vocabularies = vocabularyDao.findAll();
		for (Vocabulary v : vocabularies) {
			if (v.getIdentifier().equals("collectionTypes")) {
				logger.warn("Vocabulary [collectionTypes] exists despite db versions not containing 3.8.1; consider updating manually");
				return;
			}
		}
		
		Vocabulary v = new Vocabulary();
		v.setIdentifier("collectionTypes");
		v.setDefaultName("Collection Types");
		
		try {
			vocabularyDao.save(v);
			this.saveVersionInfo("3.8.1", false);
			logger.info("Collection types vocabulary migration completed WITHOUT errors (version: 3.8.1)");
		} catch (Exception e) {
			logger.error("Failed to update database to version 3.8.1", e);
			this.saveVersionInfo("3.8.1", true);
			logger.info("Collection types vocabulary migration completed WITH errors (version: 3.8.1)");
		}
	}

	private void migrateCollectionTypes() {
		List<String> rawCollections = this.getObjectsAsString(Collection.class);
		boolean errors = false;
		
		logger.info("Performing collection types migration (version: 3.8.2)");
		
		JsonNode node;
		ObjectNode objectNode;
		ArrayNode collectionTypesNode;
		
		Map<String, String> collectionTypesMap = new HashMap<String, String>();
		String identifier, label;
		Matcher m;
		
		for (String rawCollection : rawCollections) {
			try {
				node = objectMapper.readTree(rawCollection);
				// Skip drafts
				if (!node.path("draftUserId").isMissingNode() || !node.path("collectionType").isMissingNode()) {
					objectNode = (ObjectNode)node;
					label = objectNode.path("collectionType").textValue();
					m = LOCALIZABLE_ENTITY_IDENTIFIER_PATTERN.matcher(label);
					
					identifier = "";
					while(m.find()) {
						identifier += m.group(0);
			        }
					identifier = identifier.substring(0, 1).toLowerCase() + identifier.substring(1); 

					collectionTypesNode = objectMapper.createArrayNode();
					if (collectionTypesMap.containsKey(identifier)) {
						collectionTypesNode.add(collectionTypesMap.get(identifier));
					} else {
						VocabularyItem vi = new VocabularyItem();
						vi.setDefaultName(label);
						vi.setIdentifier(identifier);
						vi.setVocabularyIdentifier(Collection.COLLECTION_TYPES_VOCABULARY_IDENTIFIER);
						
						vocabularyItemDao.save(vi);
						
						collectionTypesNode.add(vi.getIdentifier());
						collectionTypesMap.put(identifier, vi.getIdentifier());
					}
					objectNode.set("collectionTypes", collectionTypesNode);
					objectNode.remove("collectionType");
					
					mongoTemplate.save(objectNode.toString(), DaoImpl.getCollectionName(Collection.class));
				}
			} catch (Exception e) {
				logger.error("Failed to update database to version 3.8.2", e);
				errors = true;
			}
		}
		this.saveVersionInfo("3.8.2", errors);
		logger.info("Collection types migration completed " + (errors ? "WITH" : "without") + " errors (version: 3.8.2)");
	}
	
	private void migrateImages() {
		List<String> rawCollections = this.getObjectsAsString(Collection.class);
		boolean errors = false;
		
		logger.info("Performing image migration (version: 3.8)");
		
		JsonNode node;
		ObjectNode objectNode, imagesNode;
		String imageId;
		for (String rawCollection : rawCollections) {
			try {
				node = objectMapper.readTree(rawCollection);
				/* - Convert image id under 'collectionImage' to map, 
				 * - set with collection object and
				 * - save */
				if (!node.path("collectionImage").isMissingNode()) {
					objectNode = (ObjectNode)node;
					
					imageId = objectNode.path("collectionImage").textValue();
				
					imagesNode = objectMapper.createObjectNode();
					imagesNode.put("0", imageId);
					
					objectNode.set("collectionImages", imagesNode);
					objectNode.remove("collectionImage");
					
					mongoTemplate.save(objectNode.toString(), DaoImpl.getCollectionName(Collection.class));
				}
			} catch (Exception e) {
				logger.error("Failed to update database to version 3.8", e);
				errors = true;
			}
		}
		this.saveVersionInfo("3.8", errors);
		logger.info("Image migration completed " + (errors ? "WITH" : "without") + " errors (version: 3.8)");
	}
	
	
	private void backupDb() throws Exception {
		String backupPath = backupsBasePath + File.separator + DateTime.now().toString(DateTimeFormat.forPattern("yyyyMMdd_HHmmss"));
		Files.createDirectories(Paths.get(new File(backupPath).toURI()), new FileAttribute<?>[0]);
		
		try {
			Runtime.getRuntime().exec(String.format("mongodump --out %s --db %s", backupPath, database));
		} catch (Exception e) {
			logger.error("Failed to create mongodb backup", e);
			throw e;
		}
	}
	
	private void saveVersionInfo(String version, boolean errors) {
		VersionInfo vi = new VersionInfoImpl();
		vi.setUpdateWithErrors(errors);
		vi.setVersion(version);		
		vi.setVersionHash(new String(md.digest(new String(versionHashPrefix + vi.getVersion()).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
		
		versionDao.save(vi);
	}
	
	private List<String> getObjectsAsString(Class<?> queryObject) {
		return mongoTemplate.execute(DaoImpl.getCollectionName(queryObject), new CollectionCallback<List<String>>() {
			public List<String> doInCollection(DBCollection collection) {
				DBCursor cursor = collection.find();
				List<String> result = new ArrayList<String>();
				while (cursor.hasNext()) {
					result.add(cursor.next().toString());
				}
				return result;
			}
		});
	}
}
