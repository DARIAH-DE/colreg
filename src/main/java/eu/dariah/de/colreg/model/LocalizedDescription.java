package eu.dariah.de.colreg.model;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LocalizedDescription {
	@NotBlank
	private String languageId;
	
	@NotBlank
	private String title;
	private String description;
	private String audience;
	private String provenance;
	private String acronym;
	
	
	public String getLanguageId() { return languageId; }
	public void setLanguageId(String languageId) { this.languageId = languageId; }
	
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public String getAudience() { return audience; }
	public void setAudience(String audience) { this.audience = audience; }
	
	public String getProvenance() { return provenance; }
	public void setProvenance(String provenance) { this.provenance = provenance; }
	
	public String getAcronym() { return acronym; }
	public void setAcronym(String acronym) { this.acronym = acronym; }
	
	@JsonIgnore
	public boolean isEmpty() {
		return (languageId==null || languageId.trim().isEmpty()) &&
				(title==null || title.trim().isEmpty()) &&
				(description==null || description.trim().isEmpty()) &&
				(audience==null || audience.trim().isEmpty()) &&
				(provenance==null || provenance.trim().isEmpty()) &&
				(acronym==null || acronym.trim().isEmpty());
	}
}