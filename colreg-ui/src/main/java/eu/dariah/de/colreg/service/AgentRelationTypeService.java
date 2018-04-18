package eu.dariah.de.colreg.service;

import java.util.List;

import eu.dariah.de.colreg.model.vocabulary.AgentRelationType;

public interface AgentRelationTypeService {
	public List<AgentRelationType> findAllAgentRelationTypes();
}
