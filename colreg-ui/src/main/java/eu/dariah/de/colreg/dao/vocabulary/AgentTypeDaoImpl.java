package eu.dariah.de.colreg.dao.vocabulary;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.AgentType;

@Repository
public class AgentTypeDaoImpl extends BaseDaoImpl<AgentType> implements AgentTypeDao {
	public AgentTypeDaoImpl() {
		super(AgentType.class);
	}
}
