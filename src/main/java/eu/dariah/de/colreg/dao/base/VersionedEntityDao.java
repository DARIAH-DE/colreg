package eu.dariah.de.colreg.dao.base;

import java.util.List;

import eu.dariah.de.colreg.model.base.VersionedEntityImpl;

public interface VersionedEntityDao<T extends VersionedEntityImpl> extends BaseDao<T> {
	public T findCurrentById(String id);
	public List<T> findAllCurrent();
}