package eu.dariah.de.colreg.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.AgentDao;
import eu.dariah.de.colreg.model.Agent;

@Service
public class AgentServiceImpl implements AgentService {
	@Autowired private AgentDao agentDao;

	@Override
	public List<Agent> findAllCurrent() {
		return agentDao.findAllCurrent();
	}

	@Override
	public Agent createAgent() {
		Agent a = new Agent();
		a.setId("new");
		a.setEntityId(new ObjectId().toString());
		
		return a;
	}

	@Override
	public Agent findCurrentByAgentId(String id) {
		return agentDao.findCurrentById(id);
	}
}
