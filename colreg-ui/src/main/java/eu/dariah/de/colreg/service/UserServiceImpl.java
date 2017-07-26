package eu.dariah.de.colreg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import eu.dariah.de.colreg.dao.UserDao;
import eu.dariah.de.dariahsp.model.User;
import eu.dariah.de.dariahsp.service.BaseUserService;

public class UserServiceImpl extends BaseUserService {
	@Autowired private UserDao userDetailsDao;
	
	@Override
	public User loadUserByUsername(String domain, String username) throws UsernameNotFoundException {
		return userDetailsDao.findByUsername(domain, username);
	}

	@Override
	protected void innerSaveUser(User persistedUser) {
		userDetailsDao.save(persistedUser);
	}

	public UserDetails findById(String id) {
		return userDetailsDao.findById(id);
	}

	public List<User> findByIds(List<String> ids) {
		return userDetailsDao.find(new Query(Criteria.where("id").in(ids)));
	}
}
