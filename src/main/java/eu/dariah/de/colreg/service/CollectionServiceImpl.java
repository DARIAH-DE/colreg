package eu.dariah.de.colreg.service;

import java.util.List;
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

@Service
public class CollectionServiceImpl implements CollectionService {
	@Autowired private CollectionDao collectionDao;
	@Autowired private AgentDao agentDao;

	@Override
	public Collection createCollection() {
		Collection c = new Collection();
		c.setId("new");
		c.setEntityId("new");
		
		return c;
	}

	@Override
	public void save(Collection c, String userId) {
		Collection prev = this.findCurrentByCollectionId(c.getEntityId());
		
		c.setId(null);
		if (c.getEntityId().equals("new")) {
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
		Query q = new Query();
		q.addCriteria(Criteria
				.where("succeedingVersionId").is(null)
				.and("agentRelations").elemMatch(Criteria.where("agentId").is(id)));

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
			.include("deleted")
			.include("draftUserId");
		
		return collectionDao.find(q);
	}

	@Override
	public Collection findVersionById(String id, boolean includeDeleted) {
		return collectionDao.findById(id, includeDeleted);
	}
}
