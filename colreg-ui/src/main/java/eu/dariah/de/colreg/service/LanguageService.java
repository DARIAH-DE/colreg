package eu.dariah.de.colreg.service;

import java.util.List;

import eu.dariah.de.colreg.model.vocabulary.Language;

public interface LanguageService {
	public List<Language> queryLanguages(String query);
	public Language findLanguageById(String id);
	public Language findLanguageByCode(String id);
}
