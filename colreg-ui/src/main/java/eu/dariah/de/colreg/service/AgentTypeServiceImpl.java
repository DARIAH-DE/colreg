package eu.dariah.de.colreg.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.vocabulary.AgentTypeDao;
import eu.dariah.de.colreg.model.vocabulary.AgentType;

@Service
public class AgentTypeServiceImpl implements AgentTypeService {
	@Autowired private AgentTypeDao agentTypeDao;
	
	@Override
	public List<AgentType> findAllAgentTypes() {
		return agentTypeDao.findAll();
	}
	
	@Override
	public AgentType findAgentTypeById(String agentTypeId) {
		return agentTypeDao.findOne(Query.query(Criteria.where("id").is(agentTypeId)));
	}
	
	@Override
	public Map<String, String> findAgentTypeIdLabelMap() {
		Map<String, String> agentTypeIdLabelMap = new HashMap<String, String>();
		for (AgentType t : this.findAllAgentTypes()) {
			agentTypeIdLabelMap.put(t.getId(), t.getLabel());
		}
		return agentTypeIdLabelMap;
	}
}
