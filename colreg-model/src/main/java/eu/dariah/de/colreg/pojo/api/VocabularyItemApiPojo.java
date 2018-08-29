package eu.dariah.de.colreg.pojo.api;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

import eu.dariah.de.colreg.pojo.base.BaseApiPojo;

@XmlRootElement(name="vocabulary_item")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VocabularyItemApiPojo extends BaseApiPojo {
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
	private boolean deleted;
	
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
	
	public boolean isDeleted() { return deleted; }
	public void setDeleted(boolean deleted) { this.deleted = deleted; }
	
	public Map<String, String> getLabels() { return labels; }
	public void setLabels(Map<String, String> labels) { this.labels = labels; }
}