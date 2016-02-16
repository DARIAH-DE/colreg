package eu.dariah.de.colreg.model.api.oaipmh;

import javax.xml.bind.annotation.XmlElement;

import eu.dariah.de.colreg.model.base.VersionedEntityImpl;

public class OaiPmhRecord {
	private OaiPmhRecordHeader header;
	private String metadata;
	
	@XmlElement(name="header")
	public OaiPmhRecordHeader getHeader() { return header; }
	public void setHeader(OaiPmhRecordHeader header) { this.header = header; }
	

	public String getMetadata() { return metadata; }
	public void setMetadata(String metadata) { this.metadata = metadata; }
	
	public OaiPmhRecord(VersionedEntityImpl entity, String metadata) {
		this.setHeader(new OaiPmhRecordHeader(entity));
		this.setMetadata(metadata);
	}
}