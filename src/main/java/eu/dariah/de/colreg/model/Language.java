package eu.dariah.de.colreg.model;

import eu.dariah.de.colreg.model.base.BaseIdentifiable;

public class Language extends BaseIdentifiable {
	private static final long serialVersionUID = 8711972490509670083L;

	private boolean builtIn;
	private boolean shortName;
	private boolean longName;
	
	
	public boolean isBuiltIn() { return builtIn; }
	public void setBuiltIn(boolean builtIn) { this.builtIn = builtIn; }
	
	public boolean isShortName() { return shortName; }
	public void setShortName(boolean shortName) { this.shortName = shortName; }
	
	public boolean isLongName() { return longName; }
	public void setLongName(boolean longName) { this.longName = longName; }
}
