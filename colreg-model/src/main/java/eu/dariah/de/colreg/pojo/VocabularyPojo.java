package eu.dariah.de.colreg.pojo;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class VocabularyPojo extends BaseIdentifiable {
	private static final long serialVersionUID = -5301800695287247433L;
	
	private String identifier;
	private String localizedLabel;
	
	
	public String getIdentifier() { return identifier; }
	public void setIdentifier(String identifier) { this.identifier = identifier; }
	
	public String getLocalizedLabel() { return localizedLabel; }
	public void setLocalizedLabel(String localizedLabel) { this.localizedLabel = localizedLabel; }
}