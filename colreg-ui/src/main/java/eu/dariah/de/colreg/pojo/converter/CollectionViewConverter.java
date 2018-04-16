package eu.dariah.de.colreg.pojo.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.unibamberg.minf.core.web.localization.LocaleConverter;
import eu.dariah.de.colreg.dao.vocabulary.AccessTypeDao;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.LocalizedDescription;
import eu.dariah.de.colreg.model.vocabulary.AccessType;
import eu.dariah.de.colreg.pojo.converter.base.BaseConverter;
import eu.dariah.de.colreg.pojo.view.CollectionViewPojo;

@Component
public class CollectionViewConverter extends BaseConverter<Collection, CollectionViewPojo> {

	DateTimeFormatter fmt = DateTimeFormat.forStyle("LL");
	
	@Autowired private AccessTypeDao accessTypeDao;
	@Autowired private ImageConverter imageConverter;
	
	@Override
	public List<CollectionViewPojo> convertToPojos(List<Collection> collections, Locale locale) {
		if (collections==null || collections.isEmpty()) {
			return new ArrayList<CollectionViewPojo>(0);
		}
		List<CollectionViewPojo> pojos = new ArrayList<CollectionViewPojo>(collections.size());
		Map<String, String> accessTypeIdLabelsMap = this.getAccessTypeIdLabelsMap();
		for (Collection collection : collections) {
			pojos.add(this.convertToPojo(collection, accessTypeIdLabelsMap, locale));
		}
		return pojos;
	}
	
	@Override
	public CollectionViewPojo convertToPojo(Collection collection, Locale locale) {
		return this.convertToPojo(collection, this.getAccessTypeIdLabelsMap(), locale);
	}
	

	public CollectionViewPojo convertToPojo(Collection collection, Map<String, String> accessTypeIdLabelsMap, Locale locale) {
		CollectionViewPojo pojo = new CollectionViewPojo();
		pojo.setId(collection.getEntityId());
		pojo.setDeleted(collection.isDeleted());
		pojo.setPublished(collection.getDraftUserId()==null || collection.getDraftUserId().isEmpty());
		pojo.setDraft(!pojo.isPublished());
		
		pojo.setCollectionTypeIdentifiers(collection.getCollectionTypes());

		pojo.setTimestamp(collection.getVersionTimestamp().toInstant().getMillis());
		pojo.setDisplayTimestamp(fmt.withLocale(locale).print(collection.getVersionTimestamp()));
		
		if (collection.getAccessMethods()!=null && collection.getAccessMethods().size()>0) {
			pojo.setAccessTypes(new ArrayList<String>(collection.getAccessMethods().size()));
			for (int i=0; i<collection.getAccessMethods().size(); i++) {
				pojo.getAccessTypes().add(accessTypeIdLabelsMap.get(collection.getAccessMethods().get(i).getType()));
			}
		}

		if (collection.getLocalizedDescriptions().size()==1) {
			pojo.setDisplayTitle(collection.getLocalizedDescriptions().get(0).getTitle());
		} else {
			String languageCode;
			for (LocalizedDescription desc : collection.getLocalizedDescriptions()) {
				languageCode = LocaleConverter.getLanguageForIso3Code(desc.getLanguageId());
				if (locale.getLanguage().equals(new Locale(languageCode).getLanguage())) {
					pojo.setDisplayTitle(desc.getTitle());
					break;
				}
			}
			if (pojo.getDisplayTitle()==null) {
				pojo.setDisplayTitle(collection.getLocalizedDescriptions().get(0).getTitle());
			}
		}
		
		if (collection.getCollectionImages()!=null && collection.getCollectionImages().size()>0) {
			pojo.setPrimaryImage(imageConverter.convertToPojo(collection.getCollectionImages().get(0), 0));
		}
		

		return pojo;
	}
	
	
	protected Map<String, String> getAccessTypeIdLabelsMap() {
		List<AccessType> accessTypes = accessTypeDao.findAll();
		Map<String, String> accessTypeIdLabelsMap = new HashMap<String, String>(accessTypes.size());
		for (AccessType accessType : accessTypes) {
			accessTypeIdLabelsMap.put(accessType.getId(), accessType.getLabel());
		}
		return accessTypeIdLabelsMap;
	}
}
