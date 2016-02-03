package eu.dariah.de.colreg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
		c.setEntityId(new ObjectId().toString());
		
		return c;
	}

	@Override
	public void save(Collection c) {
		Collection prev = this.findCurrentByCollectionId(c.getEntityId());
		
		c.setId(null);
		c.setSucceedingVersionId(null);
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
		Query q;
		List<Collection> result = new ArrayList<Collection>();
		List<Collection> innerResult;
		
		int maxTotalResults = 10;
		
		// TODO: Include foreName for query
		Criteria[] queryCriteria = new Criteria[] {
				// ID match
				Criteria.where("id").is(query),
				
				// Title starts with
				Criteria.where("localizedDescriptions").elemMatch(
						Criteria.where("title").regex(Pattern.compile("^" + query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
				),
				
				// Title likeness
				Criteria.where("localizedDescriptions").elemMatch(
						Criteria.where("title").regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
				)
		};
		
		for (Criteria cr : queryCriteria) {
			q = new Query();
			q.addCriteria(cr);
			q.addCriteria(Criteria.where("succeedingVersionId").is(null));
			
			if (excl!=null) {
				if (excl.size()>1) {
					q.addCriteria(Criteria.where("entityId").nin(excl));
				} else {
					q.addCriteria(Criteria.where("entityId").ne(excl.get(0)));
				}
			}
			
			q.limit(result.size() + maxTotalResults); // Could overlap
			innerResult = collectionDao.find(q);
			if (innerResult!=null && innerResult.size()>0) {
				for (Collection c : innerResult) {
					boolean contains = false;
					for (Collection cX : result) {
						if (c.getId().equals(cX.getId())) {
							contains = true;
							break;
						}
					}
					if (!contains) {
						result.add(c);
					}
				}
				if (result.size()>=maxTotalResults) {
					return result.subList(0, maxTotalResults-1);
				}
			}
			
		}
		return result;
	}

	@Override
	public List<Collection> findCurrentByParentCollectionId(String id) {
		return collectionDao.findCurrentByParentCollectionId(id);
	}
}
