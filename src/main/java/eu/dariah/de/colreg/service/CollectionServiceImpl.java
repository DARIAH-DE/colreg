package eu.dariah.de.colreg.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
}
