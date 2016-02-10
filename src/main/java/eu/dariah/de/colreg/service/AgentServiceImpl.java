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
import eu.dariah.de.colreg.dao.vocabulary.AgentTypeDao;
import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.model.Collection;

@Service
public class AgentServiceImpl implements AgentService {
	@Autowired private AgentDao agentDao;
	@Autowired private AgentTypeDao agentTypeDao;

	@Override
	public List<Agent> findAllCurrent() {
		return agentDao.findAllCurrent();
	}

	@Override
	public Agent createAgent() {
		Agent a = new Agent();
		a.setId("new");
		a.setEntityId("new");
		a.setAgentTypeId(agentTypeDao.findAll().get(0).getId());

		return a;
	}

	@Override
	public Agent findCurrentByAgentId(String id) {
		return agentDao.findCurrentById(id);
	}
	
	@Override
	public Agent findCurrentByAgentId(String id, boolean includeDeleted) {
		return agentDao.findCurrentById(id, includeDeleted);
	}

	@Override
	public List<Agent> findCurrentByParentAgentId(String id) {
		return agentDao.findCurrentByParentAgentId(id);
	}
	
	@Override
	public void save(Agent a, String userId) {
		Agent prev = this.findCurrentByAgentId(a.getEntityId());
		
		a.setId(null);
		if (a.getEntityId().equals("new")) {
			a.setEntityId(new ObjectId().toString());
		}
		
		a.setSucceedingVersionId(null);
		a.setVersionCreator(userId);
		a.setVersionTimestamp(DateTime.now());
		
		if (prev!=null) {
			a.setEntityCreator(prev.getEntityCreator());
			a.setEntityTimestamp(prev.getEntityTimestamp());
		} else {
			a.setEntityCreator(a.getVersionCreator());
			a.setEntityTimestamp(a.getVersionTimestamp());
		}
		
		agentDao.save(a);
		
		if (prev!=null) {
			prev.setSucceedingVersionId(a.getId());
			agentDao.save(prev);
		}	
	}

	@Override
	public List<Agent> queryAgents(String query, List<String> excl) {
		Criteria cBase = Criteria.where("succeedingVersionId").is(null);
		if (excl!=null) {
			if (excl.size()>1) {
				cBase.andOperator(Criteria.where("entityId").nin(excl));
			} else {
				cBase.andOperator(Criteria.where("entityId").ne(excl.get(0)));
			}
		}
		
		// TODO: Include foreName for query
		Criteria[] queryCriteria = new Criteria[] {
				// ID match
				Criteria.where("id").is(query).andOperator(cBase),
				
				// Name starts with
				Criteria.where("name").regex(Pattern.compile("^" + query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)).andOperator(cBase),
			
				// Name likeness
				Criteria.where("name").regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)).andOperator(cBase)
		};
		return agentDao.combineQueryResults(queryCriteria, 10);
	}

	@Override
	public Agent findCurrentByName(String name, String foreName) {
		Query q = new Query(Criteria
				.where("succeedingVersionId").is(null)
				.and("deleted").ne(true)
				.and("name").is(name));
		
		if (foreName!=null && !foreName.trim().equals("")) {
			q.addCriteria(Criteria.where("foreName").is(foreName.trim()));
		}
		return agentDao.findOne(q);
	}

	@Override
	public List<Agent> findAllVersionsForEntityId(String id) {
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
		
		return agentDao.find(q);
	}

	@Override
	public Agent findVersionById(String id, boolean includeDeleted) {
		return agentDao.findById(id, includeDeleted);
	}
}
