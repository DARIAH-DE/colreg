package eu.dariah.de.colreg.pojo.converter.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.CollectionAgentRelation;
import eu.dariah.de.colreg.pojo.api.AgentApiPojo;
import eu.dariah.de.colreg.pojo.api.CollectionApiPojo;
import eu.dariah.de.colreg.pojo.converter.api.AgentApiConverter;

public abstract class BaseCollectionApiConverter<TPojo extends CollectionApiPojo> extends BaseCollectionConverter<TPojo> {

	@Autowired private AgentApiConverter agentConverter;
		
	@Override
	public TPojo convertToPojo(Collection collection, Locale locale) {
		return this.convertToPojo(collection, locale, null, null);
	}
	
	public List<TPojo> convertToPojos(List<Collection> collections, Locale locale, Map<String, Agent> agentIdMap, Map<String, String> agentTypeIdLabelMap) {
		if (collections==null || collections.isEmpty()) {
			return new ArrayList<TPojo>(0);
		}
		List<TPojo> pojos = new ArrayList<TPojo>(collections.size());
		for (Collection object : collections) {
			pojos.add(this.convertToPojo(object, locale, agentIdMap, agentTypeIdLabelMap));
		}
		return pojos;
	}
	
	public TPojo convertToPojo(Collection collection, Locale locale, Map<String, Agent> agentIdMap, Map<String, String> agentTypeIdLabelMap) {
		TPojo pojo = this.createPojo();
		pojo.setId(collection.getEntityId());
		pojo.setTimestamp(collection.getVersionTimestamp().toInstant().getMillis());
		pojo.setVersionId(collection.getId());
		pojo.setCollectionTypes(collection.getCollectionTypes());
		pojo.setDeleted(collection.isDeleted());
		pojo.setPublished(collection.getDraftUserId()==null || collection.getDraftUserId().isEmpty());
		pojo.setDraft(!pojo.isPublished());

		pojo.setPrimaryImage(this.getPrimaryImage(collection));
		
		if (locale!=null) {
			pojo.setLocalizedTimestamp(this.getDisplayTimestamp(collection.getVersionTimestamp(), locale));
			pojo.setLocalizedTitle(this.getLocalizedOrDefaultTitle(collection, locale));
			pojo.setLocalizedAcronym(this.getLocalizedOrDefaultAcronym(collection, locale));
		} else {
			pojo.setAcronyms(this.getLanguageIdAcronymMap(collection));
			pojo.setTitles(this.getLanguageIdTitleMap(collection));
		}
		
		// Agents need to be preloaded and provided
		if (agentIdMap!=null && collection.getAgentRelations()!=null && collection.getAgentRelations().size()>0) {
			pojo.setAgents(new ArrayList<AgentApiPojo>());
			for (CollectionAgentRelation ar : collection.getAgentRelations()) {
				pojo.getAgents().add(agentConverter.convertToPojo(agentIdMap.get(ar.getAgentId()), locale, agentTypeIdLabelMap));
			}
		}
		return pojo;
	}
		
	protected abstract TPojo createPojo();
}
