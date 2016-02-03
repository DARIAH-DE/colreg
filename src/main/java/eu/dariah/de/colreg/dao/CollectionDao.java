package eu.dariah.de.colreg.dao;

import java.util.List;

import eu.dariah.de.colreg.dao.base.VersionedEntityDao;
import eu.dariah.de.colreg.model.Collection;

public interface CollectionDao extends VersionedEntityDao<Collection> {
	public List<Collection> findCurrentByParentCollectionId(String id);
}
