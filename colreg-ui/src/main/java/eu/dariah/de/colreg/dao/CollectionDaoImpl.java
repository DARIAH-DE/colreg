package eu.dariah.de.colreg.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.VersionedEntityDaoImpl;
import eu.dariah.de.colreg.model.Collection;

@Repository
public class CollectionDaoImpl extends VersionedEntityDaoImpl<Collection> implements CollectionDao {
	public CollectionDaoImpl() {
		super(Collection.class);
	}
	
	@Override
	public long count() {
		Criteria c = Criteria.where("succeedingVersionId").is(null).and("draftUserId").exists(false);
		Query q = new Query(c);
		return mongoTemplate.count(q, clazz);
	}
	
	@Override
	public List<Collection> findCurrentByParentCollectionId(String id) {
		return this.find(Query.query(Criteria
				.where("parentCollectionId").is(id)
				.and("deleted").ne(true)
				.and("succeedingVersionId").is(null)));
	}
	
	@Override
	public List<Collection> findAllDrafts(String userId) {
		return this.find(Query.query(Criteria.where("succeedingVersionId").is(null).and("deleted").ne(true).and("draftUserId").is(userId)));
	}

	@Override
	public long countDrafts(String userId) {
		Criteria c = Criteria.where("succeedingVersionId").is(null).and("deleted").ne(true).and("draftUserId").is(userId);
		Query q = new Query(c);
		return mongoTemplate.count(q, clazz);
	}

	@Override
	public List<Collection> findCurrentById(List<String> collectionIds) {
		Criteria[] entityIdCriteria = new Criteria[collectionIds.size()];
		for (int i=0; i<collectionIds.size(); i++) {
			entityIdCriteria[i] = Criteria.where("entityId").is(collectionIds.get(i));
		}
		return this.find(Query.query(Criteria
				.where("deleted").ne(true)
				.and("succeedingVersionId").is(null)
				.orOperator(entityIdCriteria)));
	}
}
