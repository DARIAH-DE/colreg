package eu.dariah.de.colreg.model.vocabulary.generic;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class Vocabulary extends BaseIdentifiable {
	private static final long serialVersionUID = -6245008051654334916L;
	
	private String identifier;
	private String name;
	
	public String getIdentifier() { return identifier; }
	public void setIdentifier(String identifier) { this.identifier = identifier; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}