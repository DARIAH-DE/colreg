package eu.dariah.de.colreg.dao.vocabulary;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.AccessType;

@Repository
public class AccessTypeDaoImpl extends BaseDaoImpl<AccessType> implements AccessTypeDao {
	public AccessTypeDaoImpl() {
		super(AccessType.class);
	}
}
