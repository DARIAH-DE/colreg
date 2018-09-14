package eu.dariah.de.colreg.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.AgentDao;
import eu.dariah.de.colreg.dao.CollectionDao;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.CollectionAgentRelation;
import eu.dariah.de.colreg.model.CollectionRelation;
import eu.dariah.de.colreg.service.base.BaseEntityService;
import eu.dariah.de.dariahsp.model.web.AuthPojo;

@Service
public class CollectionServiceImpl extends BaseEntityService implements CollectionService {
	protected static Logger logger = LoggerFactory.getLogger(CollectionServiceImpl.class);
	private enum CollectionRelationActionTypes { DELETE, SET }
	
	private class CollectionRelationAction {
		private final CollectionRelationActionTypes type;
		private final CollectionRelation relation;
		
		public CollectionRelationAction(CollectionRelationActionTypes type, CollectionRelation relation) {
			this.type = type;
			this.relation = relation;
		}
	}
	
	@Autowired private CollectionDao collectionDao;
	@Autowired private AgentDao agentDao;

	@Override
	public Collection createCollection(String userId) {
		Collection c = new Collection();
		c.setId("new");
		c.setEntityId("new");
		c.setDraftUserId(userId);
		
		return c;
	}

	@Override
	public void save(Collection c, String userId) {
		Collection prev = this.findCurrentByCollectionId(c.getEntityId());
		
		this.updateRelatedCollections(c, prev, userId);
		this.innerSave(c, prev, userId);
	}
		
	@Override
	public Collection findCurrentByCollectionId(String id) {
		return collectionDao.findCurrentById(id);
	}

	@Override
	public List<Collection> findAllCurrent() {
		return collectionDao.findAllCurrent();
	}
	
	@Override
	public List<Collection> findAllDrafts(String userId) {
		return collectionDao.findAllDrafts(userId);
	}

	@Override
	public void initializeAgentRelations(Collection c) {
		if (c.getAgentRelations()!=null) {
			for (CollectionAgentRelation ar : c.getAgentRelations()) {
				if (ar.getAgentId()!=null) {
					ar.setAgent(agentDao.findCurrentById(ar.getAgentId()));
				}
			}
		}
	}

	@Override
	public List<Collection> queryCollections(String query, List<String> excl) {
		Criteria cBase = Criteria.where("succeedingVersionId").is(null);
		if (excl!=null) {
			if (excl.size()>1) {
				cBase.and("entityId").nin(excl);
			} else {
				cBase.and("entityId").ne(excl.get(0));
			}
		}
		cBase.and("deleted").is(false);
		cBase.orOperator(Criteria.where("draftUserId").exists(false), Criteria.where("draftUserId").is(""));

		// TODO: Include foreName for query
		Criteria[] queryCriteria = new Criteria[] {
				// ID match
				Criteria.where("id").is(query).andOperator(cBase),

				// Title starts with
				Criteria.where("localizedDescriptions").elemMatch(
						Criteria.where("title").regex(Pattern.compile("^" + query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
				).andOperator(cBase),
				
				// Title likeness
				Criteria.where("localizedDescriptions").elemMatch(
						Criteria.where("title").regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
				).andOperator(cBase)
		};
		return collectionDao.combineQueryResults(queryCriteria, 10);
	}

	@Override
	public List<Collection> findCurrentByParentCollectionId(String id) {
		return collectionDao.findCurrentByParentCollectionId(id);
	}

	@Override
	public List<Collection> findCurrentByRelatedAgentId(String id) {
		Criteria c = Criteria.where("succeedingVersionId").is(null)
				.and("agentRelations").elemMatch(Criteria.where("agentId").is(id));
		c.orOperator(Criteria.where("draftUserId").exists(false), Criteria.where("draftUserId").is(""));
		
		Query q = new Query();
		q.addCriteria(c);		

		return collectionDao.find(q);
	}

	@Override
	public Collection findCurrentByCollectionId(String id, boolean includeDeleted) {
		return collectionDao.findCurrentById(id, includeDeleted);
	}

	@Override
	public List<Collection> findAllVersionsForEntityId(String id) {
		Query q = new Query();
		q.addCriteria(Criteria.where("entityId").is(id));
		q.with(new Sort(Sort.Direction.DESC, "versionTimestamp"));
		q.fields().include("id")
			.include("succeedingVersionId")
			.include("entityId")
			.include("versionTimestamp")
			.include("versionCreator")
			.include("versionComment")
			.include("deleted")
			.include("draftUserId");
		
		return collectionDao.find(q);
	}

	@Override
	public Collection findVersionById(String id, boolean includeDeleted) {
		return collectionDao.findById(id, includeDeleted);
	}
	
	@Override
	public void appendVersionComment(String versionid, String comment) {
		Collection c = collectionDao.findById(versionid, true);
		c.setVersionComment(comment);
		collectionDao.save(c);
	}
	
	@Override
	public List<Collection> findLatestChanges(int i, AuthPojo auth) {
		Criteria c = new Criteria();
		c.orOperator(Criteria.where("draftUserId").exists(false), Criteria.where("draftUserId").is(auth.getUserId()));
		
		Query q = new Query(c);
		q.limit(i);
		q.with(new Sort(Sort.Direction.DESC, "versionTimestamp"));
		
		return collectionDao.find(q);
	}
	
	@Override
	public long countCollections() {
		return collectionDao.count();
	}
	
	@Override
	public long countDrafts(String userId) {
		return collectionDao.countDrafts(userId);
	}

	@Override
	public List<Collection> findCurrentByCollectionIdsAndUserId(List<String> collectionIds, String userId) {
		List<Collection> collections = new ArrayList<Collection>(); 
		for (Collection c : collectionDao.findCurrentById(collectionIds)) {
			if (this.isCollectionUserAccessible(c, userId)) {
				collections.add(c);
			}
		}
		return collections;
	}
	

	private void innerSave(Collection c, Collection prev, String userId) {
		c.setId(null);
		if (c.getEntityId()==null || c.getEntityId().isEmpty() || c.getEntityId().equals("new")) {
			c.setEntityId(new ObjectId().toString());
		}
		c.setSucceedingVersionId(null);
		c.setVersionCreator(userId);
		c.setVersionTimestamp(DateTime.now());
		
		if (prev!=null) {
			c.setEntityCreator(prev.getEntityCreator());
			c.setEntityTimestamp(prev.getEntityTimestamp());
		} else {
			c.setEntityCreator(c.getVersionCreator());
			c.setEntityTimestamp(c.getVersionTimestamp());
		}
		
		c.setCollectionImages(this.getOrderedImageMap(c.getCollectionImages()));

		// Save new object first...just to be sure
		collectionDao.save(c);
		
		if (prev!=null) {
			prev.setSucceedingVersionId(c.getId());
			collectionDao.save(prev);
		}		
	}
	
	private void updateRelatedCollections(Collection collection, Collection prevCollection, String userId) {
		// Collect related collections that need to be saved
		Map<String, Collection> saveCollections = new HashMap<String, Collection>();
				
		// Collect deleted and updated collection relations
		List<CollectionRelationAction> modifiedRelations = new ArrayList<CollectionRelationAction>();
		if (prevCollection!=null && prevCollection.getRelations()!=null) {
			String relatedEntityId, relatedEntityIdDb;
			for (CollectionRelation dbCr : prevCollection.getRelations()) {
				boolean deleted = true;
				if (collection.getRelations()!=null) {
					for (CollectionRelation cr : collection.getRelations()) {
						if (cr.getId()!=null && cr.getId().equals(dbCr.getId())) {
							if (!cr.isSame(dbCr)) {
								relatedEntityId = cr.getSourceEntityId().equals(collection.getEntityId()) ? cr.getTargetEntityId() : cr.getSourceEntityId();
								relatedEntityIdDb = dbCr.getSourceEntityId().equals(collection.getEntityId()) ? dbCr.getTargetEntityId() : dbCr.getSourceEntityId();
								
								// Delete at old relation collection if related collection changed
								if (!relatedEntityId.equals(relatedEntityIdDb)) {
									modifiedRelations.add(new CollectionRelationAction(CollectionRelationActionTypes.DELETE, dbCr));
								} 
								modifiedRelations.add(new CollectionRelationAction(CollectionRelationActionTypes.SET, cr));	
							}
							deleted = false;
							break;
						}
					}
				}
				if (deleted) {
					modifiedRelations.add(new CollectionRelationAction(CollectionRelationActionTypes.DELETE, dbCr));
				}
			}
		}
		// Collect deleted and updated collection relations
		if (collection.getRelations()!=null) {
			for (CollectionRelation cr : collection.getRelations()) {
				if (cr.getId()==null || cr.getId().trim().isEmpty()) {
					cr.setId(null);
					modifiedRelations.add(new CollectionRelationAction(CollectionRelationActionTypes.SET, cr));
				}
			}
		}		
		
		Collection saveCollection;
		String relatedEntityId;
		// Iterate actions and update related collections accordingly
		for (CollectionRelationAction modifiedRelation : modifiedRelations) {
			relatedEntityId = modifiedRelation.relation.getSourceEntityId().equals(collection.getEntityId()) ? modifiedRelation.relation.getTargetEntityId() : modifiedRelation.relation.getSourceEntityId();
			if (saveCollections.containsKey(relatedEntityId)) {
				saveCollection = saveCollections.get(relatedEntityId);
			} else {			
				saveCollection = this.findCurrentByCollectionId(relatedEntityId);
				saveCollections.put(relatedEntityId, saveCollection);
			}
			
			if (modifiedRelation.type.equals(CollectionRelationActionTypes.SET) && modifiedRelation.relation.getId()==null) {
				modifiedRelation.relation.setId(new ObjectId().toString());
			}
			
			boolean processed = false;
			if (saveCollection.getRelations()!=null) {
				for (int i=0; i<saveCollection.getRelations().size(); i++) { 
					CollectionRelation relatedDbRelation = saveCollection.getRelations().get(i);
					if (relatedDbRelation.getId().equals(modifiedRelation.relation.getId())) {
						if (modifiedRelation.type.equals(CollectionRelationActionTypes.DELETE) 
								&& !this.isCollectionUserAccessible(saveCollection, userId)) {
							// Not a real deletion - the current user just could not see (and hence include) relation to draft -> reattach  
							collection.getRelations().add(relatedDbRelation);
							processed = true;
						} else if (modifiedRelation.type.equals(CollectionRelationActionTypes.DELETE)) {
							// Actual delete...
							saveCollection.getRelations().remove(relatedDbRelation);
							processed = true;
						} else {
							saveCollection.getRelations().set(i, modifiedRelation.relation);
							processed = true;
						}
						if (saveCollection.getRelations().size()==0) {
							saveCollection.setRelations(null);
						}
						break;
					}	
				}
			}
			// New
			if (!processed && modifiedRelation.type.equals(CollectionRelationActionTypes.SET)) {
				if (saveCollection.getRelations()==null) {
					saveCollection.setRelations(new ArrayList<CollectionRelation>());
				}
				saveCollection.getRelations().add(modifiedRelation.relation);
			}
			
		}
		for (String collectionId : saveCollections.keySet()) {
			logger.info(String.format("Saving collection [%s] due to altered relation with collection [%s]", collectionId, collection.getEntityId()));
			saveCollection = saveCollections.get(collectionId);
			if (this.isCollectionUserAccessible(saveCollection, userId)) {
				this.innerSave(saveCollection, this.findCurrentByCollectionId(collectionId), userId);
			}
		}
				
	}
	
	
	private boolean isCollectionUserAccessible(Collection c, String userId) {
		return c.getDraftUserId()==null || (userId!=null && c.getDraftUserId().equals(userId));
	}
}
