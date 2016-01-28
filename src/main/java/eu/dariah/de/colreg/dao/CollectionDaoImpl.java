package eu.dariah.de.colreg.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.Collection;

@Repository
public class CollectionDaoImpl extends BaseDaoImpl<Collection> implements CollectionDao {
	public CollectionDaoImpl() {
		super(Collection.class, "cols");
	}

	@Override
	public Collection findCurrentById(String id) {
		return this.findOne(Query.query(Criteria
				.where("collectionId").is(id)
				.and("previousVersionId").exists(false)));
	}
}
