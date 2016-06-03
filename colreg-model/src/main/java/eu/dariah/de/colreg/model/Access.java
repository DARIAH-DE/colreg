package eu.dariah.de.colreg.model;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class Access {
	@NotBlank
	private String type;
	
	@NotBlank(message="{~eu.dariah.de.colreg.validation.access.uri}")
	private String uri;
	private List<String> schemeIds;
	private String description;
	private String oaiSet;
	
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	
	public String getUri() { return uri; }
	public void setUri(String uri) { this.uri = uri; }
	
	public List<String> getSchemeIds() { return schemeIds; }
	public void setSchemeIds(List<String> schemeIds) { this.schemeIds = schemeIds; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public String getOaiSet() { return oaiSet; }
	public void setOaiSet(String oaiSet) { this.oaiSet = oaiSet; }
}
