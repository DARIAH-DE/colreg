package eu.dariah.de.colreg.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccessPojo {
	private String type;
	private String uri;
	private List<String> schemeIds;
	private String set;
	
	
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	
	public String getUri() { return uri; }
	public void setUri(String uri) { this.uri = uri; }
	
	public List<String> getSchemeIds() { return schemeIds; }
	public void setSchemeIds(List<String> schemeIds) { this.schemeIds = schemeIds; }
	
	public String getSet() { return set; }
	public void setSet(String set) { this.set = set; }	
}
