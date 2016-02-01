package eu.dariah.de.colreg.dao.base;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import eu.dariah.de.colreg.model.base.VersionedEntityImpl;

public abstract class VersionedEntityDaoImpl<T extends VersionedEntityImpl> extends BaseDaoImpl<T> implements VersionedEntityDao<T> {
	public VersionedEntityDaoImpl(Class<? extends T> clazz) {
		super(clazz);
	}
	
	@Override
	public <S extends T> S save(S entity) {
		entity.setCreated(DateTime.now());
		return super.save(entity);
	}
	
	@Override
	public T findCurrentById(String id) {
		return this.findOne(Query.query(Criteria
				.where("entityId").is(id)
				.and("succeedingVersionId").is(null)));
	}

	@Override
	public List<T> findAllCurrent() {
		return this.find(Query.query(Criteria.where("succeedingVersionId").is(null)));
	}
}
