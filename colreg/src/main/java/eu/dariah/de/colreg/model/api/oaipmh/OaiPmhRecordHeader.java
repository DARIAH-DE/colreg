package eu.dariah.de.colreg.model.api.oaipmh;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import eu.dariah.de.colreg.controller.api.OaiPmhController;
import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.base.VersionedEntityImpl;

public class OaiPmhRecordHeader {
	private String identifier;
	private String datestamp;
	private List<String> sets;
	
	@XmlElement(name="identifier")
	public String getIdentifier() { return identifier; }
	public void setIdentifier(String identifier) { this.identifier = identifier; }
	
	@XmlElement(name="datestamp")
	public String getDatestamp() { return datestamp; }
	public void setDatestamp(String datestamp) { this.datestamp = datestamp; }
	
	@XmlElement(name="setSpec")
	public List<String> getSets() { return sets; }
	public void setSets(List<String> sets) { this.sets = sets; }
	
	
	public OaiPmhRecordHeader() { }
	
	public OaiPmhRecordHeader(VersionedEntityImpl entity) {
		this.setIdentifier(entity.getEntityId());
		this.setDatestamp(entity.getVersionTimestamp().toString(OaiPmhController.OAI_TIMESTAMP_FORMATTER));
		this.setSets(new ArrayList<String>());
		if (Collection.class.isAssignableFrom(entity.getClass())) {
			this.getSets().add("collections");
		} else if (Agent.class.isAssignableFrom(entity.getClass())) {
			this.getSets().add("agents");
		} 
	}
}
