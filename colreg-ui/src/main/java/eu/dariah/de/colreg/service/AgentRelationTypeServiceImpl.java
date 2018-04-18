package eu.dariah.de.colreg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.vocabulary.AgentRelationTypeDao;
import eu.dariah.de.colreg.model.vocabulary.AgentRelationType;

@Service
public class AgentRelationTypeServiceImpl implements AgentRelationTypeService {
	@Autowired private AgentRelationTypeDao agentRelationTypeDao;
	
	@Override
	public List<AgentRelationType> findAllAgentRelationTypes() {
		return agentRelationTypeDao.findAll();
	}
}
