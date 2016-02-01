package eu.dariah.de.colreg.dao;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.VersionedEntityDaoImpl;
import eu.dariah.de.colreg.model.Collection;

@Repository
public class CollectionDaoImpl extends VersionedEntityDaoImpl<Collection> implements CollectionDao {
	public CollectionDaoImpl() {
		super(Collection.class);
	}
}
