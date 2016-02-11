package eu.dariah.de.colreg.service;

import java.util.List;
import java.util.Locale;

import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.pojo.AgentPojo;

public interface AgentService {
	public List<Agent> findAllCurrent();
	public Agent createAgent();
	public Agent findCurrentByAgentId(String id);
	public void save(Agent a, String userId);
	public List<Agent> queryAgents(String query, List<String> excl);
	public List<Agent> findCurrentByParentAgentId(String id);
	public Agent findCurrentByAgentId(String id, boolean includeDeleted);
	public Agent findCurrentByName(String name, String foreName);
	public List<Agent> findAllVersionsForEntityId(String id);
	public Agent findVersionById(String id, boolean includeDeleted);
	public void appendVersionComment(String versionid, String comment);
	public List<AgentPojo> convertToPojos(List<Agent> agents, Locale locale);
	public AgentPojo convertToPojo(Agent agent, Locale locale);
}
