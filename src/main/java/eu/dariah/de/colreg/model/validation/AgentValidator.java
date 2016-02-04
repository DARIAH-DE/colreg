package eu.dariah.de.colreg.model.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.service.AgentService;

@Component
public class AgentValidator implements Validator {

	@Autowired private AgentService agentService;
	
	@Override
	public boolean supports(Class<?> arg0) {
		return Agent.class.equals(arg0);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		/*
		 * 1. Unique names
		 * 2. Cycles in hierarchy
		 */
		Agent agent = (Agent)obj;
		
		Agent compareAgent = agentService.findCurrentByName(agent.getName(), agent.getForeName());
		
		if (!compareAgent.getEntityId().equals(agent.getEntityId())) {
			errors.rejectValue("name", "nonuniqe.name");
			errors.rejectValue("foreName", "nonuniqe.foreName");
		}
		
	}	
}
