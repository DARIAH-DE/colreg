package eu.dariah.de.colreg.model;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CollectionAgentRelation {
	@NotEmpty private List<String> typeIds;
	@NotBlank private String agentId;
	@JsonIgnore private Agent agent;
	private String annotation;


	public List<String> getTypeIds() { return typeIds; }
	public void setTypeIds(List<String> typeIds) { this.typeIds = typeIds; }
	
	public String getAnnotation() { return annotation; }
	public void setAnnotation(String annotation) { this.annotation = annotation; }

	public String getAgentId() { return agentId; }
	public void setAgentId(String agentId) { this.agentId = agentId; }

	public Agent getAgent() { return agent; }
	public void setAgent(Agent agent) { this.agent = agent; }
	
	@JsonIgnore
	public boolean isEmpty() {
		return (this.getTypeIds()==null || this.getTypeIds().size()==0) &&
				(this.getAnnotation()==null || this.getAnnotation().isEmpty()) &&
				(this.getAgentId()==null || this.getAgentId().isEmpty());
	}
}