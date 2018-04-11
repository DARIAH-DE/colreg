package eu.dariah.de.colreg.model.vocabulary.generic;

import eu.dariah.de.colreg.model.base.LocalizableEntity;

public class VocabularyItem extends LocalizableEntity {
	private static final long serialVersionUID = 942372659078068387L;
		
	private String vocabularyId;
	
	public String getVocabularyId() { return vocabularyId; }
	public void setVocabularyId(String vocabularyId) { this.vocabularyId = vocabularyId; }
	
	@Override
	public String getMessageCodePrefix() {
		return Vocabulary.MESSAGE_CODE_PREFIX + this.getVocabularyId() + ".";
	}
}