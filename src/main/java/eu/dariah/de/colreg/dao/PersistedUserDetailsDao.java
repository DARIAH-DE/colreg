package eu.dariah.de.colreg.dao;

import eu.dariah.de.colreg.dao.base.BaseDao;
import eu.dariah.de.colreg.model.PersistedUserDetails;

public interface PersistedUserDetailsDao extends BaseDao<PersistedUserDetails> {
	public PersistedUserDetails findByUsername(String domain, String username); 
}