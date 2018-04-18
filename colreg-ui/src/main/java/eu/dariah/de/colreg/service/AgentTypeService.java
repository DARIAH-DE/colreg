package eu.dariah.de.colreg.service;

import java.util.List;
import java.util.Map;

import eu.dariah.de.colreg.model.vocabulary.AgentType;

public interface AgentTypeService {
	public List<AgentType> findAllAgentTypes();
	public AgentType findAgentTypeById(String agentTypeId);
	public Map<String, String> findAgentTypeIdLabelMap();
}
