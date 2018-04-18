package eu.dariah.de.colreg.pojo.converter.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;

import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.pojo.converter.base.BaseAgentConverter;
import eu.dariah.de.colreg.pojo.view.AgentViewPojo;

@Component
public class AgentViewConverter extends BaseAgentConverter<AgentViewPojo> {

	@Override
	public AgentViewPojo convertToPojo(Agent agent, Locale locale) {
		return this.convertToPojo(agent, locale, null);
	}
	
	public List<AgentViewPojo> convertToPojos(List<Agent> agents, Locale locale, Map<String, String> agentTypeIdLabelMap) {
		if (agents==null || agents.isEmpty()) {
			return new ArrayList<AgentViewPojo>(0);
		}
		List<AgentViewPojo> pojos = new ArrayList<AgentViewPojo>(agents.size());
		for (Agent object : agents) {
			pojos.add(this.convertToPojo(object, locale, agentTypeIdLabelMap));
		}
		return pojos;
	}
	
	public AgentViewPojo convertToPojo(Agent agent, Locale locale, Map<String, String> agentTypeIdLabelMap) {
		AgentViewPojo pojo = new AgentViewPojo();
		pojo.setId(agent.getEntityId());
		pojo.setDeleted(agent.isDeleted());
		pojo.setDisplayTimestamp(this.getDisplayTimestamp(agent.getVersionTimestamp(), locale));
		pojo.setName(this.getDisplayName(agent));
		pojo.setTimestamp(agent.getVersionTimestamp().toInstant().getMillis());
		if (agentTypeIdLabelMap!=null) {
			pojo.setType(agentTypeIdLabelMap.get(agent.getAgentTypeId()));
		}
		return pojo;
	}

}
