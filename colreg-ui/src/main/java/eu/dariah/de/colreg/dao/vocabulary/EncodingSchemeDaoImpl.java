package eu.dariah.de.colreg.dao.vocabulary;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.EncodingScheme;

@Repository
public class EncodingSchemeDaoImpl extends BaseDaoImpl<EncodingScheme> implements EncodingSchemeDao {
	public EncodingSchemeDaoImpl() {
		super(EncodingScheme.class);
	}
}
