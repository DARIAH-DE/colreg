package eu.dariah.de.colreg.model.vocabulary.generic;

import eu.dariah.de.colreg.model.base.LocalizableEntity;

public class VocabularyItem extends LocalizableEntity {
	private static final long serialVersionUID = 942372659078068387L;
		
	private String vocabularyIdentifier;
	private boolean deleted;
	
	public String getVocabularyIdentifier() { return vocabularyIdentifier; }
	public void setVocabularyIdentifier(String vocabularyIdentifier) { this.vocabularyIdentifier = vocabularyIdentifier; }
	
	public boolean isDeleted() { return deleted; }
	public void setDeleted(boolean deleted) { this.deleted = deleted; }
	
	@Override
	public String getMessageCodePrefix() {
		return Vocabulary.MESSAGE_CODE_PREFIX + this.getVocabularyIdentifier() + ".";
	}
}