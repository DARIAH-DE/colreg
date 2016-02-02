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
		Query q;
		List<Agent> result = new ArrayList<Agent>();
		List<Agent> innerResult;
		
		int maxTotalResults = 10;
		
		// TODO: Include foreName for query
		Criteria[] queryCriteria = new Criteria[] {
				// ID match
				Criteria.where("id").is(query),
				
				// Name starts with
				Criteria.where("name").regex(Pattern.compile("^" + query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)),
			
				// Name likeness
				Criteria.where("name").regex(Pattern.compile(query, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
		};
		
		for (Criteria c : queryCriteria) {
			q = new Query();
			q.addCriteria(c);
			q.addCriteria(Criteria.where("succeedingVersionId").is(null));
			
			if (excl!=null) {
				if (excl.size()>1) {
					q.addCriteria(Criteria.where("entityId").nin(excl));
				} else {
					q.addCriteria(Criteria.where("entityId").ne(excl.get(0)));
				}
			}
			
			q.limit(result.size() + maxTotalResults); // Could overlap
			innerResult = agentDao.find(q);
			if (innerResult!=null && innerResult.size()>0) {
				for (Agent a : innerResult) {
					boolean contains = false;
					for (Agent aX : result) {
						if (a.getId().equals(aX.getId())) {
							contains = true;
							break;
						}
					}
					if (!contains) {
						result.add(a);
					}
				}
				if (result.size()>=maxTotalResults) {
					return result.subList(0, maxTotalResults-1);
				}
			}
			
		}
		return result;
	}
}
