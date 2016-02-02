package eu.dariah.de.colreg.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CollectionAgentRelation {
	private List<String> typeIds;
	private String annotation;
	private String agentId;
	@JsonIgnore private Agent agent;

	
	public List<String> getTypeIds() { return typeIds; }
	public void setTypeIds(List<String> typeIds) { this.typeIds = typeIds; }
	
	public String getAnnotation() { return annotation; }
	public void setAnnotation(String annotation) { this.annotation = annotation; }

	public String getAgentId() { return agentId; }
	public void setAgentId(String agentId) { this.agentId = agentId; }

	public Agent getAgent() { return agent; }
	public void setAgent(Agent agent) { this.agent = agent; }
}