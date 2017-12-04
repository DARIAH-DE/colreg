package eu.dariah.de.colreg.model.vocabulary;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class UnitOfMeasurement extends BaseIdentifiable {
	private static final long serialVersionUID = 8711972490509670083L;

	private String messageCode;
	private String name;
	
	public String getMessageCode() { return messageCode; }
	public void setMessageCode(String messageCode) { this.messageCode = messageCode; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}