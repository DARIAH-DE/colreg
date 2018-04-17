package eu.dariah.de.colreg.pojo.converter.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;

import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.pojo.api.AgentApiPojo;
import eu.dariah.de.colreg.pojo.converter.base.BaseConverter;

@Component
public class AgentApiConverter extends BaseConverter<Agent, AgentApiPojo> {
	
	@Override
	public AgentApiPojo convertToPojo(Agent agent, Locale locale) {
		return this.convertToPojo(agent, locale, null);
	}
	
	public List<AgentApiPojo> convertToPojos(List<Agent> agents, Locale locale, Map<String, String> agentTypeIdLabelMap) {
		if (agents==null || agents.isEmpty()) {
			return new ArrayList<AgentApiPojo>(0);
		}
		List<AgentApiPojo> pojos = new ArrayList<AgentApiPojo>(agents.size());
		for (Agent object : agents) {
			pojos.add(this.convertToPojo(object, locale, agentTypeIdLabelMap));
		}
		return pojos;
	}
	
	public AgentApiPojo convertToPojo(Agent agent, Locale locale, Map<String, String> agentTypeIdLabelMap) {
		AgentApiPojo pojo = new AgentApiPojo();
		pojo.setId(agent.getEntityId());
		pojo.setVersionId(agent.getId());
		pojo.setTimestamp(agent.getVersionTimestamp().toInstant().getMillis());
		pojo.setDeleted(agent.isDeleted());
		pojo.setName(agent.getName() + ((agent.getForeName()!=null && !agent.getForeName().trim().isEmpty()) ? ", " + agent.getForeName(): ""));
		
		if (locale!=null) {
			pojo.setLocalizedTimestamp(this.getDisplayTimestamp(agent.getVersionTimestamp(), locale));
		}
		
		if (agentTypeIdLabelMap!=null) {
			pojo.setType(agentTypeIdLabelMap.get(agent.getAgentTypeId()));
		}
		return pojo;
	}
}
