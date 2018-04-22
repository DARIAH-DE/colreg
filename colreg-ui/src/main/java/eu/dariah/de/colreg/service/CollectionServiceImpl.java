package eu.dariah.de.colreg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.AgentDao;
import eu.dariah.de.colreg.dao.CollectionDao;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.CollectionAgentRelation;
import eu.dariah.de.dariahsp.model.web.AuthPojo;

@Service
public class CollectionServiceImpl implements CollectionService {
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
	public Map<Integer, String> getOrderedImageMap(Map<Integer, String> imageMap) {
		if (imageMap!=null) {
			List<Integer> dropImages = new ArrayList<Integer>();
			
			String imageId;
			for (Integer index : imageMap.keySet()) {
				imageId = imageMap.get(index);
				if (imageId==null || imageId.trim().isEmpty()) {
					dropImages.add(index);
				}
			}
			if (dropImages.size()>0) {
				for (Integer dropIndex : dropImages) {
					imageMap.remove(dropIndex.intValue());
				}
			}
			if (imageMap.size()==0) {
				return null;
			}
			
			imageMap = new TreeMap<Integer, String>(imageMap);
			Map<Integer, String> result = new TreeMap<Integer, String>();
			int idxNew = 0;
			for (Integer index : imageMap.keySet()) {
				result.put(idxNew++, imageMap.get(index));
			}
			imageMap = result;
		}
		return imageMap;
	}

	@Override
	public List<Collection> findCurrentByCollectionIds(List<String> collectionIds) {
		return collectionDao.findCurrentById(collectionIds);
	}
}
