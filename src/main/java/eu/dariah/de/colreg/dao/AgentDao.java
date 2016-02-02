package eu.dariah.de.colreg.dao;

import java.util.List;

import eu.dariah.de.colreg.dao.base.VersionedEntityDao;
import eu.dariah.de.colreg.model.Agent;

public interface AgentDao extends VersionedEntityDao<Agent> {
	public List<Agent> findCurrentByParentAgentId(String id);
}
