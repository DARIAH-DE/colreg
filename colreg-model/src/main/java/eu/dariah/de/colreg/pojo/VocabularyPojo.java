package eu.dariah.de.colreg.pojo;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class VocabularyPojo extends BaseIdentifiable {
	private static final long serialVersionUID = -5301800695287247433L;
	
	private String identifier;
	private String localizedlabel;
	
	
	public String getIdentifier() { return identifier; }
	public void setIdentifier(String identifier) { this.identifier = identifier; }
	
	public String getLocalizedlabel() { return localizedlabel; }
	public void setLocalizedlabel(String localizedlabel) { this.localizedlabel = localizedlabel; }
}