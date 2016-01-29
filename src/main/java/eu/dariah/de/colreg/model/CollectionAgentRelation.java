package eu.dariah.de.colreg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CollectionAgentRelation {
	private String typeId;
	private String annotation;
	private String agentId;
	@JsonIgnore private Agent agent;

	
	public String getTypeId() { return typeId; }
	public void setTypeId(String typeId) { this.typeId = typeId; }
 
	public String getAnnotation() { return annotation; }
	public void setAnnotation(String annotation) { this.annotation = annotation; }

	public String getAgentId() { return agentId; }
	public void setAgentId(String agentId) { this.agentId = agentId; }

	public Agent getAgent() { return agent; }
	public void setAgent(Agent agent) { this.agent = agent; }
}