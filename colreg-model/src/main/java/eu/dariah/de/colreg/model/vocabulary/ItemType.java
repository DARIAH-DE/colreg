package eu.dariah.de.colreg.model.vocabulary;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class ItemType extends BaseIdentifiable {
	private static final long serialVersionUID = 7869029170227297640L;
	
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
