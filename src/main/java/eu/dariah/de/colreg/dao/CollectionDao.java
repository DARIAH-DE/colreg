package eu.dariah.de.colreg.dao;

import java.util.List;

import eu.dariah.de.colreg.dao.base.BaseDao;
import eu.dariah.de.colreg.model.Collection;

public interface CollectionDao extends BaseDao<Collection> {
	public Collection findCurrentById(String id);
	public List<Collection> findAllCurrent();
}
