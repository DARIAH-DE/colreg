package eu.dariah.de.colreg.model.api.oaipmh;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="OAI-PMH")
public class OaiPmhResponseContainer {
	private String responseDate;
	private OaiPmhRequest request;
	
	private OaiPmhRecordWrapper record;
	private List<OaiPmhRecordWrapper> records;
	
	@XmlElement
	public String getResponseDate() { return responseDate; }
	public void setResponseDate(String responseDate) { this.responseDate = responseDate; }
	
	public OaiPmhRequest getRequest() { return request; }
	public void setRequest(OaiPmhRequest request) { this.request = request; }
	
	@XmlElement(name="GetRecord")
	public OaiPmhRecordWrapper getRecord() { return record; }
	public void setRecord(OaiPmhRecordWrapper record) { this.record = record; }
	
	@XmlElement(name="ListRecords")
	public List<OaiPmhRecordWrapper> getRecords() { return records; }
	public void setRecords(List<OaiPmhRecordWrapper> records) { this.records = records; }	
}