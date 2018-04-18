package eu.dariah.de.colreg.pojo.api;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class VocabularyItemApiPojo extends BaseIdentifiable {
	private static final long serialVersionUID = -7649200109797919221L;
	private String identifier;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String externalIdentifier;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String localizedLabel;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Map<String, String> labels;
	
	private String defaultName;
	private String vocabularyId;
	private boolean disabled;
	
	public String getVocabularyId() { return vocabularyId; }
	public void setVocabularyId(String vocabularyId) { this.vocabularyId = vocabularyId; }
	
	public String getIdentifier() { return identifier; }
	public void setIdentifier(String identifier) { this.identifier = identifier; }
	
	public String getExternalIdentifier() { return externalIdentifier; }
	public void setExternalIdentifier(String externalIdentifier) { this.externalIdentifier = externalIdentifier; }
	
	public String getLocalizedLabel() { return localizedLabel; }
	public void setLocalizedLabel(String localizedLabel) { this.localizedLabel = localizedLabel; }
		
	public String getDefaultName() { return defaultName; }
	public void setDefaultName(String defaultName) { this.defaultName = defaultName; }
	
	public boolean isDisabled() { return disabled; }
	public void setDisabled(boolean disabled) { this.disabled = disabled; }
	
	public Map<String, String> getLabels() { return labels; }
	public void setLabels(Map<String, String> labels) { this.labels = labels; }
}