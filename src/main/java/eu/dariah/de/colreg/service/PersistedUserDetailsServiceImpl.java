package eu.dariah.de.colreg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.PersistedUserDetailsDao;
import eu.dariah.de.colreg.model.PersistedUserDetails;
import eu.dariah.de.colreg.service.PersistedUserDetailsService;

@Service
public class PersistedUserDetailsServiceImpl implements PersistedUserDetailsService {
	@Autowired private PersistedUserDetailsDao userDetailsDao;
	
	@Override
	public PersistedUserDetails loadUserByUsername(String domain, String username) throws UsernameNotFoundException {
		return userDetailsDao.findByUsername(domain, username);
	}

	@Override
	public void saveUser(PersistedUserDetails persistedUser) {
		userDetailsDao.save(persistedUser);
	}

	@Override
	public PersistedUserDetails findById(String id) {
		return userDetailsDao.findById(id);
	}

	@Override
	public List<PersistedUserDetails> findByIds(List<String> fetchedUserIds) {
		return userDetailsDao.find(new Query(Criteria.where("id").in(fetchedUserIds)));
	}
}
