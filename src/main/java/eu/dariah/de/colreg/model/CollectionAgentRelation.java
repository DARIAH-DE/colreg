package eu.dariah.de.colreg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CollectionAgentRelation {
	private String type;
	private String annotation;
	private String agentId;
	@JsonIgnore private Agent agent;

	
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
 
	public String getAnnotation() { return annotation; }
	public void setAnnotation(String annotation) { this.annotation = annotation; }

	public String getAgentId() { return agentId; }
	public void setAgentId(String agentId) { this.agentId = agentId; }

	public Agent getAgent() { return agent; }
	public void setAgent(Agent agent) { this.agent = agent; }
}