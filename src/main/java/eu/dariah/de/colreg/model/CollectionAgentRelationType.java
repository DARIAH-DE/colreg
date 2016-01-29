package eu.dariah.de.colreg.model;

import eu.dariah.de.colreg.model.base.BaseIdentifiable;

public class CollectionAgentRelationType extends BaseIdentifiable {
	private static final long serialVersionUID = -5636572505149778721L;
	
	private String label;
	private String description;
	
	
	public String getLabel() { return label; }
	public void setLabel(String label) { this.label = label; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }	
}
