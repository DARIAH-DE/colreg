package eu.dariah.de.colreg.model.vocabulary;

import eu.dariah.de.minfba.core.metamodel.BaseIdentifiable;

public class AgentRelationType extends BaseIdentifiable {
	private static final long serialVersionUID = 8613883963063275262L;
	
	private String identifier;
	private String label;
	private String description;
	
	public String getIdentifier() { return identifier; }
	public void setIdentifier(String identifier) { this.identifier = identifier; }
	
	public String getLabel() { return label; }
	public void setLabel(String label) { this.label = label; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
}