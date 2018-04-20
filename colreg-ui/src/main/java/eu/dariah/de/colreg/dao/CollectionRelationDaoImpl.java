package eu.dariah.de.colreg.dao;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.CollectionRelation;

@Repository
public class CollectionRelationDaoImpl extends BaseDaoImpl<CollectionRelation> implements CollectionRelationDao {
	public CollectionRelationDaoImpl() {
		super(CollectionRelation.class);
	}
}
