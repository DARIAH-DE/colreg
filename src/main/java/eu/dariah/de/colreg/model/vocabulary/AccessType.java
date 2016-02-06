package eu.dariah.de.colreg.model.vocabulary;

import eu.dariah.de.colreg.model.base.BaseIdentifiable;

public class AccessType extends BaseIdentifiable {
	private static final long serialVersionUID = -237738480385781253L;
	
	private String identifier;
	private boolean machineAccessible;
	private String label;
	private String description;
	
	public String getIdentifier() { return identifier; }
	public void setIdentifier(String identifier) { this.identifier = identifier; }
	
	public boolean isMachineAccessible() { return machineAccessible; }
	public void setMachineAccessible(boolean machineAccessible) { this.machineAccessible = machineAccessible; }
	
	public String getLabel() { return label; }
	public void setLabel(String label) { this.label = label; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
}
