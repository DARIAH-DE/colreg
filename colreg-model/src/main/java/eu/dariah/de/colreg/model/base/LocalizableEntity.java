package eu.dariah.de.colreg.model.base;

import org.hibernate.validator.constraints.NotBlank;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public abstract class LocalizableEntity extends BaseIdentifiable {
	private static final long serialVersionUID = -96310693915725198L;
	
	@NotBlank(message="{~eu.dariah.de.colreg.validation.vocabulary_item.identifier_blank}")
	private String identifier;
	
	@NotBlank(message="{~eu.dariah.de.colreg.validation.vocabulary_item.default_name_blank}")
	private String defaultName;
	
	public String getIdentifier() { return identifier; }
	public void setIdentifier(String identifier) { this.identifier = identifier; }
	
	public String getDefaultName() { return defaultName; }
	public void setDefaultName(String defaultName) { this.defaultName = defaultName; }
	
	
	public String getMessageCode() {
		if (this.identifier==null || this.identifier.trim().isEmpty()) {
			return null;
		}
		return this.getMessageCodePrefix() + this.identifier;
	}
	
	public abstract String getMessageCodePrefix();
}
