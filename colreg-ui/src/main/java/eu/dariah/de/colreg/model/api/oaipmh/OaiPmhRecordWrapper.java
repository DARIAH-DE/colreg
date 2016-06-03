package eu.dariah.de.colreg.model.api.oaipmh;

import javax.xml.bind.annotation.XmlElement;

import eu.dariah.de.colreg.model.base.VersionedEntityImpl;

public class OaiPmhRecordWrapper {
	private OaiPmhRecord record;

	@XmlElement(name="record")
	public OaiPmhRecord getRecord() { return record; }
	public void setRecord(OaiPmhRecord record) { this.record = record; }
	
	
	public OaiPmhRecordWrapper() {}
	
	public OaiPmhRecordWrapper(VersionedEntityImpl entity, String metadata) {
		this.setRecord(new OaiPmhRecord(entity, metadata));
	}
}
