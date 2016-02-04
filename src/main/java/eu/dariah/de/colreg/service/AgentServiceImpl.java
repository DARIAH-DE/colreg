package eu.dariah.de.colreg.service;

import java.util.List;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.AgentDao;
import eu.dariah.de.colreg.dao.vocabulary.AgentTypeDao;
import eu.dariah.de.colreg.model.Agent;

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
		a.setEntityId(new ObjectId().toString());
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
	public void save(Agent a) {
		Agent prev = this.findCurrentByAgentId(a.getEntityId());
		
		a.setId(null);
		a.setSucceedingVersionId(null);
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
}
