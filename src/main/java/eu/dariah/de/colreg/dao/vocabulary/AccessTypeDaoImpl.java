package eu.dariah.de.colreg.dao.vocabulary;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.AccessType;

public class AccessTypeDaoImpl extends BaseDaoImpl<AccessType> implements AccessTypeDao {
	public AccessTypeDaoImpl() {
		super(AccessType.class);
	}
}
