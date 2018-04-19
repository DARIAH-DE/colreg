package eu.dariah.de.colreg.pojo.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VocabularyItemViewPojo extends BaseIdentifiable implements Comparable<VocabularyItemViewPojo> {
	private static final long serialVersionUID = -3342978755459281019L;
	
	private String identifier;
	private String externalIdentifier;
	private String localizedLabel;
	private boolean hasCurrentLocale;
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
	
	public boolean isHasCurrentLocale() { return hasCurrentLocale; }
	public void setHasCurrentLocale(boolean hasCurrentLocale) { this.hasCurrentLocale = hasCurrentLocale; }
	
	public String getDefaultName() { return defaultName; }
	public void setDefaultName(String defaultName) { this.defaultName = defaultName; }
	
	public boolean isDeleted() { return deleted; }
	public void setDeleted(boolean deleted) { this.deleted = deleted; }
	
	public String getDisplayLabel() {
		if (this.hasCurrentLocale) {
			return this.localizedLabel;
		} else {
			return this.defaultName;
		}
	}
	
	@Override
	public int compareTo(VocabularyItemViewPojo o) {
		return this.getDisplayLabel().compareTo(o.getDisplayLabel());
	}
}