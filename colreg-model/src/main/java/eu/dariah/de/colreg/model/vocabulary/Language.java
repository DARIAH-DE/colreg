package eu.dariah.de.colreg.model.vocabulary;

import eu.dariah.de.colreg.model.base.BaseIdentifiable;

public class Language extends BaseIdentifiable {
	private static final long serialVersionUID = 8711972490509670083L;

	private String code;
	private String name;
	
	public String getCode() { return code; }
	public void setCode(String code) { this.code = code; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}