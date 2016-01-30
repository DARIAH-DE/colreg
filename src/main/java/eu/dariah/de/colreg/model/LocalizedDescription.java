package eu.dariah.de.colreg.model;

public class LocalizedDescription {
	private String languageId;
	private String title;
	private String description;
	private String audience;
	private String provenance;
	private String acronym;
	private boolean collectionDefault;
	
	
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
	
	public boolean isCollectionDefault() { return collectionDefault; }
	public void setCollectionDefault(boolean collectionDefault) { this.collectionDefault = collectionDefault; }
}