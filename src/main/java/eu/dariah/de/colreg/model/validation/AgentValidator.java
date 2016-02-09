package eu.dariah.de.colreg.model.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.model.vocabulary.AgentType;
import eu.dariah.de.colreg.service.AgentService;
import eu.dariah.de.colreg.service.VocabularyService;

@Component
public class AgentValidator extends BaseValidator<Agent> {

	@Autowired private AgentService agentService;
	@Autowired private VocabularyService vocabularyService;
	
	public AgentValidator() {
		super(Agent.class);
	}


	@Override
	public void preprocess(Agent agent) {
		if (!vocabularyService.findAgentTypeById(agent.getAgentTypeId()).isNaturalPerson()) {
			agent.setForeName(null);
		}
	}


	@Override
	public void innerValidate(Agent agent, Errors errors) {
		
		if (vocabularyService.findAgentTypeById(agent.getAgentTypeId()).isNaturalPerson() &&
				(agent.getForeName()==null || agent.getForeName().trim().isEmpty())) {
			errors.rejectValue("foreName", "~must.be.set", "~must.be.set");
		}
		
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
