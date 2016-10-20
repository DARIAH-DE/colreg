package eu.dariah.de.colreg.model.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import eu.dariah.de.colreg.model.Address;
import eu.dariah.de.colreg.model.Agent;
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
		
		// Remove the completely empty addresses
		if (agent.getAddresses()!=null && agent.getAddresses().size()>0) {
			List<Address> emptyAddresses = new ArrayList<Address>();
			for (Address addr : agent.getAddresses()) {
				if (addr.isEmpty()) {
					emptyAddresses.add(addr);
				}
			}
			agent.getAddresses().removeAll(emptyAddresses);
		}
		
		// Remove empty identifiers
		if (agent.getProvidedIdentifier()!=null && agent.getProvidedIdentifier().size()>0) {
			List<String> retainIdentifiers = new ArrayList<String>();
			for (String id : agent.getProvidedIdentifier()) {
				if (id!=null && !id.trim().isEmpty()) {
					retainIdentifiers.add(id);
				}
			}
			agent.setProvidedIdentifier(retainIdentifiers);
		}
		
		// Prefix http://
		if (agent.getWebPage()!=null && !agent.getWebPage().trim().isEmpty() && 
				!agent.getWebPage().toLowerCase().trim().startsWith("http")) {
			agent.setWebPage("http://" + agent.getWebPage().trim());
		}
	}

	public List<String> getEntityWarnings(Agent agent) {
		List<String> warnings = new ArrayList<String>();
		
		// Unique names of agents (warning for natural persons)
		Agent compareAgent = agentService.findCurrentByName(agent.getName(), agent.getForeName());
		if (compareAgent!=null && !compareAgent.getEntityId().equals(agent.getEntityId())) {
			if (agentService.findAgentTypeById(compareAgent.getAgentTypeId()).isNaturalPerson() && 
					agentService.findAgentTypeById(agent.getAgentTypeId()).isNaturalPerson()) {
				warnings.add("~eu.dariah.de.colreg.validation.agent.natural_name_not_unique");
			}
		}
		return warnings;
	}
	
	@Override
	public void innerValidate(Agent agent, Errors errors) {
		
		if (vocabularyService.findAgentTypeById(agent.getAgentTypeId()).isNaturalPerson() &&
				(agent.getForeName()==null || agent.getForeName().trim().isEmpty())) {
			errors.rejectValue("foreName", "~eu.dariah.de.colreg.validation.agent.first_name");
		}
		
		// Unique names of agents (error for organizations)
		Agent compareAgent = agentService.findCurrentByName(agent.getName(), agent.getForeName());
		if (compareAgent!=null && !compareAgent.getEntityId().equals(agent.getEntityId())) {
			if (!agentService.findAgentTypeById(compareAgent.getAgentTypeId()).isNaturalPerson() || 
					!agentService.findAgentTypeById(agent.getAgentTypeId()).isNaturalPerson()) {
				errors.rejectValue("name", "~eu.dariah.de.colreg.validation.agent.name_not_unique");
				errors.rejectValue("foreName", "~eu.dariah.de.colreg.validation.agent.name_not_unique");
			}
		}
	}
}
