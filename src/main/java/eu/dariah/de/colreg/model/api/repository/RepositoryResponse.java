package eu.dariah.de.colreg.model.api.repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="response")
public class RepositoryResponse {
	private String status;
	private String url;
	private String xml;
	private String error;
	
	@XmlElement(name="status")
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	
	@XmlElement(name="url")
	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
	
	@XmlElement(name="xml")
	public String getXml() { return xml; }
	public void setXml(String xml) { this.xml = xml; }
	
	@XmlElement(name="error")
	public String getError() { return error; }
	public void setError(String error) { this.error = error; }	
}