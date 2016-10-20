package eu.dariah.de.colreg.model.vocabulary;

import java.beans.Transient;

import eu.dariah.de.colreg.model.base.BaseIdentifiable;

public class License extends BaseIdentifiable {
	private static final long serialVersionUID = 8705409920105900752L;
	
	private LicenseGroup group;
	private String identifier;
	private String label;
	private String url;
	
	
	@Transient
	public LicenseGroup getGroup() { return group; }
	public void setGroup(LicenseGroup group) { this.group = group; }
	
	public String getIdentifier() { return identifier; }
	public void setIdentifier(String identifier) { this.identifier = identifier; }
	
	public String getLabel() { return label; }
	public void setLabel(String label) { this.label = label; }
	
	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
}