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

import org.bson.types.ObjectId;
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
import eu.dariah.de.colreg.model.CollectionRelation;
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
			this.createVocabulary(Collection.COLLECTION_TYPES_VOCABULARY_IDENTIFIER, "Collection Types", "3.8.1");
		}
		if (!existingVersions.contains("3.8.2")) {
			if (!backedUp) {
				this.backupDb();
				backedUp = true;
			}
			this.migrateCollectionTypes();
		}
		if (!existingVersions.contains("3.9.0")) {
			if (!backedUp) {
				this.backupDb();
				backedUp = true;
			}
			this.createVocabulary(Collection.ITEM_TYPES_VOCABULARY_IDENTIFIER, "Item Types", "3.9.0");
		}
		if (!existingVersions.contains("3.9.1")) {
			if (!backedUp) {
				this.backupDb();
				backedUp = true;
			}
			this.migrateItemTypes();
		}
		if (!existingVersions.contains("3.9.2")) {
			if (!backedUp) {
				this.backupDb();
				backedUp = true;
			}
			this.migrateCollectionRelations();
		}
	}
	
	private void migrateCollectionRelations() {
		this.createVocabulary(CollectionRelation.COLLECTION_RELATION_TYPES_VOCABULARY_IDENTIFIER, "Collection Relation Types", "3.9.2");
		
		// Existing hierarchical relations
		VocabularyItem vi = new VocabularyItem();
		vi.setIdentifier("childOf");
		vi.setDefaultName("is child of");
		vi.setVocabularyIdentifier(CollectionRelation.COLLECTION_RELATION_TYPES_VOCABULARY_IDENTIFIER);
		vocabularyItemDao.save(vi);
		
		// generic 'related to'
		vi = new VocabularyItem();
		vi.setIdentifier("relatedTo");
		vi.setDefaultName("is related to");
		vi.setVocabularyIdentifier(CollectionRelation.COLLECTION_RELATION_TYPES_VOCABULARY_IDENTIFIER);
		vocabularyItemDao.save(vi);
		
		ObjectNode objectNode;
		Map<String, ObjectNode> rawCollectionsIdMap = new HashMap<String, ObjectNode>();
		for (String rawCollection : this.getObjectsAsString("collection")) {
			try {
				objectNode = (ObjectNode)objectMapper.readTree(rawCollection);
				// We only update the latest versions of the collections for simplicity reasons
				if (objectNode.get("succeedingVersionId")==null || objectNode.get("succeedingVersionId").isMissingNode()
						 || objectNode.get("succeedingVersionId").asText().trim().isEmpty()) {
					rawCollectionsIdMap.put(objectNode.path("entityId").asText(), objectNode);
				}
			} catch (Exception e) {
				logger.error("Failed to update database to version 3.9.2", e);
				return;
			}
		}
		
		String parentCollectionId;		
		ObjectNode targetNode, relationNode, idNode;
		try {
			for (String collectionId : rawCollectionsIdMap.keySet()) {
				objectNode = rawCollectionsIdMap.get(collectionId);
				if (objectNode.get("parentCollectionId")==null || objectNode.get("parentCollectionId").isMissingNode()) {
					continue;
				}
				parentCollectionId = objectNode.get("parentCollectionId").textValue();
				if (parentCollectionId.trim().isEmpty()) {
					continue;
				}
				objectNode.remove("parentCollectionId");
				
				idNode = objectMapper.createObjectNode();
				idNode.put("$oid", new ObjectId().toString());
				
				relationNode = objectMapper.createObjectNode();
				relationNode.set("_id", idNode);
				relationNode.put("sourceEntityId", collectionId);
				relationNode.put("targetEntityId", parentCollectionId);
				relationNode.put("relationTypeId", "childOf");
				
				this.appendRelationToCollection(objectNode, relationNode);
				mongoTemplate.save(objectNode.toString(), DaoImpl.getCollectionName(Collection.class));
				
				targetNode = rawCollectionsIdMap.get(parentCollectionId);
				this.appendRelationToCollection(targetNode, relationNode);
				mongoTemplate.save(targetNode.toString(), DaoImpl.getCollectionName(Collection.class));
			}
			logger.info("Collection Relation Types migration completed WITHOUT errors (version: 3.9.2)");
		} catch (Exception e) {
			logger.error("Failed to update database to version 3.9.2", e);
		}
	}
	
	private void appendRelationToCollection(ObjectNode collectionNode, ObjectNode relationNode) {
		ArrayNode relationsNode;
		if (collectionNode.get("relations")!=null && !collectionNode.get("relations").isMissingNode()) {
			relationsNode = (ArrayNode)collectionNode.get("relations");
		} else {
			relationsNode = objectMapper.createArrayNode();
		}
		relationsNode.add(relationNode);
		collectionNode.set("relations", relationsNode);
	}

	private void createVocabulary(String identifier, String displayName, String version) {
		logger.info(String.format("Performing vocabulary migration (version: %s; vocabulary: %s)", version, displayName));
		
		List<Vocabulary> vocabularies = vocabularyDao.findAll();
		for (Vocabulary v : vocabularies) {
			if (v.getIdentifier().equals(identifier)) {
				logger.warn(String.format("Vocabulary [%s] exists despite db versions not containing %s; consider updating manually", identifier, version));
				return;
			}
		}
		
		Vocabulary v = new Vocabulary();
		v.setIdentifier(identifier);
		v.setDefaultName(displayName);
		
		try {
			vocabularyDao.save(v);
			this.saveVersionInfo(version, false);
			logger.info(String.format("Item types vocabulary migration completed WITHOUT errors (version: 3.9)", version, displayName));
		} catch (Exception e) {
			logger.error(String.format("Failed to update database (version: %s; vocabulary: %s)", version, displayName));
			this.saveVersionInfo(version, true);
			logger.info(String.format("Item types vocabulary migration completed WITH errors (version: %s; vocabulary: %s)", version, displayName));
		}
	}
		
	private void migrateItemTypes() {
		logger.info("Performing item types migration (version: 3.9.1)");
		boolean errors = false;
		
		try {
			Map<String, String> itemTypesMap = this.createItemTypes();
			this.updateCollectionsItemTypes(itemTypesMap);
			mongoTemplate.dropCollection("itemType");
		} catch (Exception e) {
			errors = true;
		}
		
		this.saveVersionInfo("3.9.1", errors);
		logger.info("Item types migration completed " + (errors ? "WITH" : "without") + " errors (version: 3.9.1)");
	}
	
	private Map<String, String> createItemTypes() throws Exception {
		JsonNode node;
		ObjectNode objectNode;
		String identifier, label;
		Matcher m;
		
		VocabularyItem vi;
		
		Map<String, String> itemTypesMap = new HashMap<String, String>();
		
		List<String> rawItemTypes = this.getObjectsAsString("itemType");
		for (String rawItemType : rawItemTypes) {
			try {
				node = objectMapper.readTree(rawItemType);
				objectNode = (ObjectNode)node;
				
				label = objectNode.path("label").textValue();
				m = LOCALIZABLE_ENTITY_IDENTIFIER_PATTERN.matcher(label);
				
				identifier = "";
				while(m.find()) {
					identifier += m.group(0);
		        }
				identifier = identifier.substring(0, 1).toLowerCase() + identifier.substring(1); 
				
				vi = new VocabularyItem();
				vi.setIdentifier(identifier);
				vi.setExternalIdentifier(node.path("identifier").isMissingNode() ? "" : node.path("identifier").textValue());
				vi.setDefaultName(node.path("label").isMissingNode() ? "" : node.path("label").textValue());
				vi.setDescription(node.path("description").isMissingNode() ? "" : node.path("description").textValue());
				vi.setVocabularyIdentifier(Collection.ITEM_TYPES_VOCABULARY_IDENTIFIER);
				
				vocabularyItemDao.save(vi);

				itemTypesMap.put(node.path("_id").path("$oid").asText(), identifier);
				
			} catch (Exception e) {
				logger.error("Failed to update database to version 3.9.1", e);
				throw e;
			}
		}
		
		return itemTypesMap;
	}
	
	private void updateCollectionsItemTypes(Map<String, String> itemTypesMap) throws Exception {
		List<String> rawCollections = this.getObjectsAsString("collection");
		
		JsonNode node;
		ObjectNode objectNode;
		ArrayNode itemTypesNode, itemTypeIdsNode;
		
		try {
			for (String rawCollection : rawCollections) {
			
				node = objectMapper.readTree(rawCollection);
				objectNode = (ObjectNode)node;
				if (objectNode.get("itemTypeIds")==null || objectNode.get("itemTypeIds").isMissingNode()) {
					continue;
				}
				itemTypesNode = objectMapper.createArrayNode();
				itemTypeIdsNode = (ArrayNode)objectNode.get("itemTypeIds");
				
				for (JsonNode itemTypeIdNode : itemTypeIdsNode) {
					if (itemTypesMap.containsKey(itemTypeIdNode.textValue())) {
						itemTypesNode.add(itemTypesMap.get(itemTypeIdNode.textValue()));
					} else {
						throw new Exception("Failed to resolve itemType ID; database might be corrupt: restore backup and migrate manually");
					}
				}
				
				objectNode.set("itemTypes", itemTypesNode);
				objectNode.remove("itemTypeIds");
				
				mongoTemplate.save(objectNode.toString(), DaoImpl.getCollectionName(Collection.class));
			}
		} catch (Exception e) {
			logger.error("Failed to update database to version 3.9.1", e);
			throw e;
		}
	}
	
	private void migrateCollectionTypes() {
		List<String> rawCollections = this.getObjectsAsString("collection");
		boolean errors = false;
		
		logger.info("Performing collection types migration (version: 3.8.2)");
		
		JsonNode node;
		ObjectNode objectNode;
		ArrayNode collectionTypesNode;
		
		List<String> collectionTypeIdentifiers = new ArrayList<String>();
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
					if (collectionTypeIdentifiers.contains(identifier)) {
						collectionTypeIdentifiers.add(identifier);
					} else {
						VocabularyItem vi = new VocabularyItem();
						vi.setDefaultName(label);
						vi.setIdentifier(identifier);
						vi.setVocabularyIdentifier(Collection.COLLECTION_TYPES_VOCABULARY_IDENTIFIER);
						
						vocabularyItemDao.save(vi);
						collectionTypeIdentifiers.add(identifier);
					}
					collectionTypesNode.add(identifier);
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
		List<String> rawCollections = this.getObjectsAsString("collection");
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
	
	private List<String> getObjectsAsString(String queryObject) {
		return mongoTemplate.execute(queryObject, new CollectionCallback<List<String>>() {
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
