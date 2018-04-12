package eu.dariah.de.colreg.service;

import java.util.List;

import eu.dariah.de.colreg.model.vocabulary.generic.VocabularyItem;

public interface VocabularyItemService {
	public List<VocabularyItem> findVocabularyItems(String vocabularyId);

	public VocabularyItem createVocabularyItem(String vocabularyId);

	public VocabularyItem findVocabularyItemById(String vocabularyId, String vocabularyItemId);
}
