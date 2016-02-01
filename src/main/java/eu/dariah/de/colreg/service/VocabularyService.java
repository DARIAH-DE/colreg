package eu.dariah.de.colreg.service;

import java.util.List;

import eu.dariah.de.colreg.model.vocabulary.AccessType;
import eu.dariah.de.colreg.model.vocabulary.AccrualMethod;
import eu.dariah.de.colreg.model.vocabulary.AccrualPolicy;
import eu.dariah.de.colreg.model.vocabulary.Language;

public interface VocabularyService {
	public List<Language> queryLanguages(String query);
	
	public List<AccrualMethod> findAllAccrualMethods();
	public List<AccrualPolicy> findAllAccrualPolicies();
	public List<AccessType> findAllAccessTypes();
}
