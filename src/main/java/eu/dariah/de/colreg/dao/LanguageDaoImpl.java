package eu.dariah.de.colreg.dao;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.Language;

@Repository
public class LanguageDaoImpl extends BaseDaoImpl<Language> implements LanguageDao {
	public LanguageDaoImpl() {
		super(Language.class);
	}
}
