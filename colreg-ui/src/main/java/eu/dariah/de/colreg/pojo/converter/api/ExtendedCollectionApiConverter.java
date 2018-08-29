package eu.dariah.de.colreg.pojo.converter.api;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.pojo.api.ExtendedCollectionApiPojo;
import eu.dariah.de.colreg.pojo.converter.AccessConverter;
import eu.dariah.de.colreg.pojo.converter.AccrualConverter;
import eu.dariah.de.colreg.pojo.converter.base.BaseCollectionApiConverter;

@Component
public class ExtendedCollectionApiConverter extends BaseCollectionApiConverter<ExtendedCollectionApiPojo> {

	@Autowired private AccrualConverter accrualConverter;
	@Autowired private AccessConverter accessConverter;
	
	@Override
	protected ExtendedCollectionApiPojo createPojo() {
		return new ExtendedCollectionApiPojo();
	}
	
	@Override
	public ExtendedCollectionApiPojo convertToPojo(Collection collection, Locale locale, Map<String, Agent> agentIdMap, Map<String, String> agentTypeIdLabelMap) {
		return this.convertToPojo(collection, locale, agentIdMap, agentTypeIdLabelMap, null, null, null, null);
	}
	
	public ExtendedCollectionApiPojo convertToPojo(Collection collection, Locale locale, Map<String, Agent> agentIdMap, 
			Map<String, String> agentTypeIdLabelMap, Map<String, String> accessTypeIdLabelMap, Map<String, String> accrualMethodIdIdentifierMap, 
			Map<String, String> accrualPolicyIdIdentifierMap, Map<String, String> accrualPeriodicityIdIdentifierMap) {
		ExtendedCollectionApiPojo pojo = super.convertToPojo(collection, locale, agentIdMap, agentTypeIdLabelMap);
		
		pojo.setAccessPojos(accessConverter.convertToPojos(collection.getAccessMethods(), locale, accessTypeIdLabelMap));
		pojo.setAccrualPojos(accrualConverter.convertToPojos(collection.getAccrualMethods(), locale, accrualMethodIdIdentifierMap, accrualPolicyIdIdentifierMap, accrualPeriodicityIdIdentifierMap));
		pojo.setWebPage(collection.getWebPage());
		pojo.seteMail(collection.getEMail());
		pojo.setCurationDriven(collection.isCurationDriven());
		pojo.setResearchDriven(collection.isResearchDriven());
		pojo.setImages(this.getImages(collection));
		
		if (locale!=null) {
			pojo.setLocalizedDescription(this.getLocalizedOrDefaultDescription(collection, locale));
		} else {
			pojo.setDecriptions(this.getLanguageIdDescriptionMap(collection));
		}
		return pojo;
	}
}
