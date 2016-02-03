package eu.dariah.de.colreg.model;

import java.util.List;

import eu.dariah.de.colreg.model.vocabulary.EncodingScheme;

public class Access {
	private String type;
	private String uri;
	private List<String> schemeIds;
	
	
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	
	public String getUri() { return uri; }
	public void setUri(String uri) { this.uri = uri; }
	
	public List<String> getSchemeIds() { return schemeIds; }
	public void setSchemeIds(List<String> schemeIds) { this.schemeIds = schemeIds; }
}
