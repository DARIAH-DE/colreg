package eu.dariah.de.colreg.pojo.converter.api;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;

import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.LocalizedDescription;
import eu.dariah.de.colreg.pojo.api.ExtendedCollectionApiPojo;
import eu.dariah.de.colreg.pojo.converter.base.BaseCollectionApiConverter;

@Component
public class ExtendedCollectionApiConverter extends BaseCollectionApiConverter<ExtendedCollectionApiPojo> {

	@Override
	protected ExtendedCollectionApiPojo createPojo() {
		return new ExtendedCollectionApiPojo();
	}
	
	@Override
	public ExtendedCollectionApiPojo convertToPojo(Collection collection, Locale locale, Map<String, Agent> agentIdMap, Map<String, String> agentTypeIdLabelMap) {
		ExtendedCollectionApiPojo pojo = super.convertToPojo(collection, locale, agentIdMap, agentTypeIdLabelMap);
		
		//pojo.setAccessPojos(convertAccessToPojos(collection.getAccessMethods()));
		//pojo.setAccrualPojos(convertAccrualToPojos(collection.getAccrualMethods()));
		pojo.setWebPage(collection.getWebPage());
		pojo.seteMail(collection.getEMail());
		pojo.setCurationDriven(collection.isCurationDriven());
		pojo.setResearchDriven(collection.isResearchDriven());
		pojo.setImages(this.getImages(collection));
		
		if (locale!=null) {
			pojo.setLocalizedDescription(this.getLocalizedOrDefaultDescription(collection, locale));
		} else {
			pojo.setTitles(this.getLanguageIdDescriptionMap(collection));
		}
		return pojo;
	}
}
