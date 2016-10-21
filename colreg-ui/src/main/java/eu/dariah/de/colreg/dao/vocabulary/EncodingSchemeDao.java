package eu.dariah.de.colreg.dao.vocabulary;

import java.util.List;

import eu.dariah.de.colreg.dao.base.BaseDao;
import eu.dariah.de.colreg.model.vocabulary.EncodingScheme;

public interface EncodingSchemeDao extends BaseDao<EncodingScheme> {
	void deleteAll();
	void saveAll(List<EncodingScheme> s);
}
