package eu.dariah.de.colreg.model.vocabulary.generic;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class Vocabulary extends BaseIdentifiable {
	private static final long serialVersionUID = -6245008051654334916L;
	
	public static final String MESSAGE_CODE_PREFIX = "~eu.dariah.de.colreg.vocabularies.";
	
	private String identifier;
	private String defaultName;
	
	public String getIdentifier() { return identifier; }
	public void setIdentifier(String identifier) { this.identifier = identifier; }
	
	public String getDefaultName() { return defaultName; }
	public void setDefaultName(String defaultName) { this.defaultName = defaultName; }
	
	
	public String getMessageCode() {
		if (this.identifier==null || this.identifier.trim().isEmpty()) {
			return null;
		}
		return MESSAGE_CODE_PREFIX + this.identifier;
	}
}