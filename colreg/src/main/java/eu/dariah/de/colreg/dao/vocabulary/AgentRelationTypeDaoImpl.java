package eu.dariah.de.colreg.dao.vocabulary;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.AgentRelationType;

@Repository
public class AgentRelationTypeDaoImpl extends BaseDaoImpl<AgentRelationType> implements AgentRelationTypeDao {
	public AgentRelationTypeDaoImpl() {
		super(AgentRelationType.class);
	}
}
