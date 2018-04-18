package eu.dariah.de.colreg.service;

import java.util.List;

import eu.dariah.de.colreg.model.vocabulary.generic.VocabularyItem;

public interface VocabularyItemService {
	public List<VocabularyItem> findVocabularyItems(String vocabularyIdentifier);
	
	public VocabularyItem createVocabularyItem(String vocabularyIdentifier);

	public VocabularyItem findVocabularyItemById(String vocabularyIdentifier, String vocabularyItemId);

	public List<VocabularyItem> findVocabularyItemByIdentifier(String vocabularyIdentifier, String identifier);
	
	public void saveVocabularyItem(VocabularyItem vocabularyItem);
}
