package eu.dariah.de.colreg.model.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.service.AgentService;

@Component
public class AgentValidator extends BaseValidator<Agent> {

	@Autowired private AgentService agentService;
	
	public AgentValidator() {
		super(Agent.class);
	}


	@Override
	public void preprocess(Agent agent) {}


	@Override
	public void validate(Agent agent, Errors errors) {
		/*
		 * 1. Unique names
		 * 2. Cycles in hierarchy
		 */
		Agent compareAgent = agentService.findCurrentByName(agent.getName(), agent.getForeName());
		
		if (compareAgent!=null && !compareAgent.getEntityId().equals(agent.getEntityId())) {
			errors.rejectValue("name", "nonuniqe.name");
			errors.rejectValue("foreName", "nonuniqe.foreName");
		}
	}
}
