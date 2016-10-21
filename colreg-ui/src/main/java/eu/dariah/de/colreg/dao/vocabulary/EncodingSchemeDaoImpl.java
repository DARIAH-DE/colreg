package eu.dariah.de.colreg.dao.vocabulary;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.EncodingScheme;

@Repository
public class EncodingSchemeDaoImpl extends BaseDaoImpl<EncodingScheme> implements EncodingSchemeDao {
	public EncodingSchemeDaoImpl() {
		super(EncodingScheme.class);
	}

	@Override
	public void deleteAll() {
		mongoTemplate.remove(Query.query(Criteria.where(ID_FIELD).exists(true)), this.getCollectionName());
	}

	@Override
	public void saveAll(List<EncodingScheme> s) {
		mongoTemplate.insert(s, this.getCollectionName());
	}
}
