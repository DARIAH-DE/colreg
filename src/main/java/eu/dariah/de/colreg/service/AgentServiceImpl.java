package eu.dariah.de.colreg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.AgentDao;

@Service
public class AgentServiceImpl implements AgentService {
	@Autowired private AgentDao agentDao;
}
