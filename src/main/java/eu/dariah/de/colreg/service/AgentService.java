package eu.dariah.de.colreg.service;

import java.util.List;

import eu.dariah.de.colreg.model.Agent;

public interface AgentService {
	public List<Agent> findAllCurrent();
	public Agent createAgent();
	public Agent findCurrentByAgentId(String id);
	public void save(Agent a);
	public List<Agent> queryAgents(String query, List<String> excl);
	public List<Agent> findCurrentByParentAgentId(String id);
}
