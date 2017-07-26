package eu.dariah.de.colreg.model.vocabulary;

import eu.dariah.de.minfba.core.metamodel.BaseIdentifiable;

public class AgentType extends BaseIdentifiable {
	private static final long serialVersionUID = -5716283876953029548L;
	
	private String identifier;
	private String label;
	private String description;
	private boolean naturalPerson;
	
	
	public String getIdentifier() { return identifier; }
	public void setIdentifier(String identifier) { this.identifier = identifier; }
	
	public String getLabel() { return label; }
	public void setLabel(String label) { this.label = label; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public boolean isNaturalPerson() { return naturalPerson; }
	public void setNaturalPerson(boolean naturalPerson) { this.naturalPerson = naturalPerson; }
}
