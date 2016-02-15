package eu.dariah.de.colreg.model.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import eu.dariah.de.colreg.model.Collection;

@XmlRootElement(name="actionObject", namespace="http://mydomain2/screen/2.0")
public class ActionObject {
	public enum INGEST_ACTION { PUT, UPDATE, DELETE }
	
	private String verfiedUser;
	private String verifiedEndpoint;
	
	private INGEST_ACTION action;
	
	private Collection collection;

	@XmlElement(name="user", namespace="api::test:ns:2012")
	public String getVerfiedUser() { return verfiedUser; }
	public void setVerfiedUser(String verfiedUser) { this.verfiedUser = verfiedUser; }

	@XmlTransient
	public String getVerifiedEndpoint() { return verifiedEndpoint; }
	public void setVerifiedEndpoint(String verifiedEndpoint) { this.verifiedEndpoint = verifiedEndpoint; }

	public INGEST_ACTION getAction() { return action; }
 	public void setAction(INGEST_ACTION action) { this.action = action; }

	public Collection getCollection() { return collection; }
	public void setCollection(Collection collection) { this.collection = collection; }
}