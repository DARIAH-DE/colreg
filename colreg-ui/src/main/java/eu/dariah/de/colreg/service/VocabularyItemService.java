package eu.dariah.de.colreg.service;

import java.util.List;

import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.vocabulary.generic.VocabularyItem;

public interface VocabularyItemService {
	public List<VocabularyItem> findVocabularyItems(String vocabularyIdentifier);
	
	public VocabularyItem createVocabularyItem(String vocabularyIdentifier);

	public VocabularyItem findVocabularyItemById(String vocabularyItemId);

	public List<VocabularyItem> findVocabularyItemByIdentifier(String vocabularyIdentifier, String identifier);
	
	public void saveVocabularyItem(VocabularyItem vocabularyItem);

	public int deleteVocabularyItem(VocabularyItem vi, String userId);

	/**
	 * Returns all published collections and user drafts that hold the queried vocabularyItemIdentifier under 
	 * 	the specified vocabularyIdentifier 
	 * 
	 * @param vocabularyIdentifier The relevant vocabulary/field
	 * @param vocabularyItemIdentifier Identifier of the queried vocabulary item
	 * @param userId User for drafts
	 * @return
	 */
	public List<Collection> findCurrentMatchingCollections(String vocabularyIdentifier, String vocabularyItemIdentifier, String userId);
}
