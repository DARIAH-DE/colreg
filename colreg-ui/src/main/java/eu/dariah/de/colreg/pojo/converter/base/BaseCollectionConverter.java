package eu.dariah.de.colreg.pojo.converter.base;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import de.unibamberg.minf.core.web.localization.LocaleConverter;
import de.unibamberg.minf.dme.model.base.BaseIdentifiable;
import eu.dariah.de.colreg.dao.vocabulary.AccessTypeDao;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.LocalizedDescription;
import eu.dariah.de.colreg.model.vocabulary.AccessType;
import eu.dariah.de.colreg.pojo.ImagePojo;
import eu.dariah.de.colreg.pojo.converter.ImageConverter;

public abstract class BaseCollectionConverter<TPojo extends BaseIdentifiable> extends BaseConverter<Collection, TPojo> {

	@Autowired private AccessTypeDao accessTypeDao;
	@Autowired private ImageConverter imageConverter;
		
	protected ImagePojo getPrimaryImage(Collection collection) {
		if (collection.getCollectionImages()!=null && collection.getCollectionImages().size()>0) {
			return imageConverter.convertToPojo(collection.getCollectionImages().get(0), 0);
		}
		return null;
	}
	
	protected List<ImagePojo> getImages(Collection collection) {
		if (collection.getCollectionImages()!=null && collection.getCollectionImages().size()>0) {
			return imageConverter.convertToPojos(collection.getCollectionImages());
		}
		return null;
	}
	
	protected Map<String, String> getLanguageIdTitleMap(Collection collection) {
		Map<String, String> titles = new HashMap<String, String>();
		for (LocalizedDescription desc : collection.getLocalizedDescriptions()) {
			titles.put(desc.getLanguageId(), desc.getTitle());
		}
		return titles;
	}

	protected Map<String, String> getLanguageIdAcronymMap(Collection collection) {
		Map<String, String> acronyms = new HashMap<String, String>();
		for (LocalizedDescription desc : collection.getLocalizedDescriptions()) {
			if (desc.getAcronym()!=null && !desc.getAcronym().isEmpty()) {
				acronyms.put(desc.getLanguageId(), desc.getAcronym());
			}
		}
		return acronyms;
	}
	
	protected Map<String, String> getLanguageIdDescriptionMap(Collection collection) {
		Map<String, String> descriptions = new HashMap<String, String>();
		for (LocalizedDescription desc : collection.getLocalizedDescriptions()) {
			if (desc.getDescription()!=null && !desc.getDescription().isEmpty()) {
				descriptions.put(desc.getLanguageId(), desc.getDescription());
			}
		}
		return descriptions;
	}
	
	protected String getLocalizedOrDefaultTitle(Collection collection, Locale locale) {
		return this.getLocalizedOrDefaultLocalization(collection, locale).getTitle();
	}
	
	protected String getLocalizedOrDefaultAcronym(Collection collection, Locale locale) {
		return this.getLocalizedOrDefaultLocalization(collection, locale).getAcronym();
	}
	
	protected String getLocalizedOrDefaultDescription(Collection collection, Locale locale) {
		return this.getLocalizedOrDefaultLocalization(collection, locale).getDescription();
	}
	
	protected LocalizedDescription getLocalizedOrDefaultLocalization(Collection collection, Locale locale) {
		if (locale==null || collection.getLocalizedDescriptions().size()==1) {
			return collection.getLocalizedDescriptions().get(0);
		} else {
			String languageCode;
			for (LocalizedDescription desc : collection.getLocalizedDescriptions()) {
				languageCode = LocaleConverter.getLanguageForIso3Code(desc.getLanguageId());
				if (locale.getLanguage().equals(new Locale(languageCode).getLanguage())) {
					return desc;
				}
			}
			return collection.getLocalizedDescriptions().get(0);
		}
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
