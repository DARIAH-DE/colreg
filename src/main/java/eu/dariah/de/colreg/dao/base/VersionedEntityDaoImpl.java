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
		return super.save(entity);
	}
	
	@Override
	public T findCurrentById(String id) {
		return this.findCurrentById(id, false);
	}
	
	@Override
	public T findCurrentById(String id, boolean includeDeleted) {
		Query q = new Query();
		q.addCriteria(Criteria
				.where("entityId").is(id)
				.and("succeedingVersionId").is(null));
				
		if (!includeDeleted) {
			q.addCriteria(Criteria.where("deleted").ne(true));
		}
		return this.findOne(q);
	}

	@Override
	public List<T> findAllCurrent() {
		return this.find(Query.query(Criteria.where("succeedingVersionId").is(null).and("deleted").ne(true)));
	}
	
	@Override
	public List<T> findAllCurrent(boolean includeDeleted) {
		Query q = new Query();
		q.addCriteria(Criteria.where("succeedingVersionId").is(null));
		if (!includeDeleted) {
			q.addCriteria(Criteria.where("deleted").ne(true));
		}
		return this.find(q);
	}
	
	@Override
	public T findById(String id, boolean includeDeleted) {
		Query q = new Query();
		q.addCriteria(Criteria.where("id").is(id));
		if (!includeDeleted) {
			q.addCriteria(Criteria.where("deleted").ne(true));
		}
		return this.findOne(q);
	}
}
