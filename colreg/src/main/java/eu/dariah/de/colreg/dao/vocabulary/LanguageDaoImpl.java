package eu.dariah.de.colreg.dao.vocabulary;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.Language;

@Repository
public class LanguageDaoImpl extends BaseDaoImpl<Language> implements LanguageDao {
	public LanguageDaoImpl() {
		super(Language.class);
	}
}
