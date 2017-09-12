package eu.dariah.de.colreg.model.vocabulary;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class CollectionType extends BaseIdentifiable {
	private static final long serialVersionUID = 5254354255079831197L;
	
	private String label;
	private String description;
	
	
	public String getLabel() { return label; }
	public void setLabel(String label) { this.label = label; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
}
