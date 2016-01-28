package eu.dariah.de.colreg.dao;

import eu.dariah.de.colreg.dao.base.BaseDao;
import eu.dariah.de.colreg.model.Collection;

public interface CollectionDao extends BaseDao<Collection> {

	Collection findCurrentById(String id);

}
