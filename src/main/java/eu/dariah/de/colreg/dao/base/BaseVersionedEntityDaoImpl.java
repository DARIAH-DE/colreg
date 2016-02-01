package eu.dariah.de.colreg.dao.base;

import org.joda.time.DateTime;

import eu.dariah.de.colreg.model.base.VersionedEntityImpl;

public abstract class BaseVersionedEntityDaoImpl<T extends VersionedEntityImpl> extends BaseDaoImpl<T> {
	public BaseVersionedEntityDaoImpl(Class<? extends T> clazz) {
		super(clazz);
	}
	
	@Override
	public <S extends T> S save(S entity) {
		entity.setCreated(DateTime.now());
		return super.save(entity);
	}
}
