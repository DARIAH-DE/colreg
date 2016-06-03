package eu.dariah.de.colreg.model.api.oaipmh;

import javax.xml.bind.annotation.XmlAttribute;

public class OaiPmhRequest {
	private String verb;
	private String id;
	private String metadataPrefix;
	private String from;
	private String until;
	private String set;
	private String resumptionToken;
	
	@XmlAttribute(name="verb")
	public String getVerb() { return verb; }
	public void setVerb(String verb) { this.verb = verb; }
	
	@XmlAttribute(name="identifier")
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	
	@XmlAttribute(name="metadataPrefix")
	public String getMetadataPrefix() { return metadataPrefix;}
	public void setMetadataPrefix(String metadataPrefix) { this.metadataPrefix = metadataPrefix; }
	
	@XmlAttribute(name="from")
	public String getFrom() { return from; }
	public void setFrom(String from) { this.from = from; }
	
	@XmlAttribute(name="until")
	public String getUntil() { return until; }
	public void setUntil(String until) { this.until = until; }
	
	@XmlAttribute(name="set")
	public String getSet() { return set; }
	public void setSet(String set) { this.set = set; }
	
	@XmlAttribute(name="resumptionToken")
	public String getResumptionToken() { return resumptionToken; }
	public void setResumptionToken(String resumptionToken) { this.resumptionToken = resumptionToken; } 
}
