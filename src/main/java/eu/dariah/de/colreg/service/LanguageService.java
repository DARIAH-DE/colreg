package eu.dariah.de.colreg.service;

import java.util.List;

import eu.dariah.de.colreg.model.Language;

public interface LanguageService {
	public List<Language> queryLanguages(String query);
}
