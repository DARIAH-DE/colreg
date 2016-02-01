package eu.dariah.de.colreg.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
		a.setEntityId(new ObjectId().toString());
		a.setAgentTypeId(agentTypeDao.findAll().get(0).getId());

		return a;
	}

	@Override
	public Agent findCurrentByAgentId(String id) {
		return agentDao.findCurrentById(id);
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
}
