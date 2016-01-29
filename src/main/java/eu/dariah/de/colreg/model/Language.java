package eu.dariah.de.colreg.model;

import eu.dariah.de.colreg.model.base.BaseIdentifiable;

public class Language extends BaseIdentifiable {
	private static final long serialVersionUID = 8711972490509670083L;

	private boolean code;
	private boolean name;
	
	
	public boolean isCode() { return code; }
	public void setCode(boolean code) { this.code = code; }
	
	public boolean isName() { return name; }
	public void setName(boolean name) { this.name = name; }
}