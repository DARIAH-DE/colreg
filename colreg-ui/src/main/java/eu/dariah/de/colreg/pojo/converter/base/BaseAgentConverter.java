package eu.dariah.de.colreg.pojo.converter.base;

import eu.dariah.de.colreg.model.Agent;

public abstract class BaseAgentConverter<TPojo> extends BaseConverter<Agent, TPojo> {

	
	protected String getDisplayName(Agent agent) {
		return agent.getName() + ((agent.getForeName()!=null && !agent.getForeName().trim().isEmpty()) ? ", " + agent.getForeName(): "");
	}
	
}
