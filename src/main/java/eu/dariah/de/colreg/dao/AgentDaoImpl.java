package eu.dariah.de.colreg.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.VersionedEntityDaoImpl;
import eu.dariah.de.colreg.model.Agent;

@Repository
public class AgentDaoImpl extends VersionedEntityDaoImpl<Agent> implements AgentDao {
	public AgentDaoImpl() {
		super(Agent.class);
	}

	@Override
	public List<Agent> findCurrentByParentAgentId(String id) {
		return this.find(Query.query(Criteria
				.where("parentAgentId").is(id)
				.and("deleted").ne(true)
				.and("succeedingVersionId").is(null)));
	}
}
