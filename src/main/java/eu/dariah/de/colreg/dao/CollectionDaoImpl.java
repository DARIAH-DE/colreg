package eu.dariah.de.colreg.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.Collection;

@Repository
public class CollectionDaoImpl extends BaseDaoImpl<Collection> implements CollectionDao {
	public CollectionDaoImpl() {
		super(Collection.class);
	}

	@Override
	public Collection findCurrentById(String id) {
		return this.findOne(Query.query(Criteria
				.where("collectionId").is(id)
				.and("succeedingVersionId").is(null)));
	}

	@Override
	public List<Collection> findAllCurrent() {
		return this.find(Query.query(Criteria.where("succeedingVersionId").is(null)));
	}
}
