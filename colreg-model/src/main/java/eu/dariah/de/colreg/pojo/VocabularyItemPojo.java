package eu.dariah.de.colreg.pojo;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class VocabularyItemPojo extends BaseIdentifiable implements Comparable<VocabularyItemPojo> {
	private static final long serialVersionUID = -3342978755459281019L;
	
	private String identifier;
	private String localizedLabel;
	private boolean hasCurrentLocale;
	private String defaultName;
	private String vocabularyId;
	
	
	public String getVocabularyId() { return vocabularyId; }
	public void setVocabularyId(String vocabularyId) { this.vocabularyId = vocabularyId; }
	
	public String getIdentifier() { return identifier; }
	public void setIdentifier(String identifier) { this.identifier = identifier; }
	
	public String getLocalizedLabel() { return localizedLabel; }
	public void setLocalizedLabel(String localizedLabel) { this.localizedLabel = localizedLabel; }
	
	public boolean isHasCurrentLocale() { return hasCurrentLocale; }
	public void setHasCurrentLocale(boolean hasCurrentLocale) { this.hasCurrentLocale = hasCurrentLocale; }
	
	public String getDefaultName() { return defaultName; }
	public void setDefaultName(String defaultName) { this.defaultName = defaultName; }
	
	public String getDisplayLabel() {
		if (this.hasCurrentLocale) {
			return this.localizedLabel;
		} else {
			return this.defaultName;
		}
	}
	
	@Override
	public int compareTo(VocabularyItemPojo o) {
		return this.getDisplayLabel().compareTo(o.getDisplayLabel());
	}
}