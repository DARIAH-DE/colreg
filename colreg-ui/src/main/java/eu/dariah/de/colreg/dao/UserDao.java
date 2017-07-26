package eu.dariah.de.colreg.dao;

import eu.dariah.de.colreg.dao.base.BaseDao;
import eu.dariah.de.dariahsp.model.User;

public interface UserDao extends BaseDao<User> {
	public User findByUsername(String domain, String username);
}