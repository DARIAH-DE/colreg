package eu.dariah.de.colreg.service;

import java.util.List;

import eu.dariah.de.colreg.model.vocabulary.generic.Vocabulary;

public interface VocabularyService {
	public List<Vocabulary> findVocabularies();
	public Vocabulary findVocabulary(String vocabularyId);
	public Vocabulary findVocabularyByIdentifier(String identifier);
}
