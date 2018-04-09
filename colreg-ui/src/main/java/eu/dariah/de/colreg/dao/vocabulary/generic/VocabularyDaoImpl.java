package eu.dariah.de.colreg.dao.vocabulary.generic;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.generic.Vocabulary;

@Repository
public class VocabularyDaoImpl extends BaseDaoImpl<Vocabulary> implements VocabularyDao {
	public VocabularyDaoImpl() {
		super(Vocabulary.class);
	}
}
