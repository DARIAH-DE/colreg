package eu.dariah.de.colreg.dao;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.VersionedEntityDaoImpl;
import eu.dariah.de.colreg.model.Agent;

@Repository
public class AgentDaoImpl extends VersionedEntityDaoImpl<Agent> implements AgentDao {
	public AgentDaoImpl() {
		super(Agent.class);
	}
}
