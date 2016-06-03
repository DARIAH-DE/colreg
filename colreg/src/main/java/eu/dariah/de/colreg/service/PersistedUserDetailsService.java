package eu.dariah.de.colreg.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import eu.dariah.de.colreg.model.PersistedUserDetails;

public interface PersistedUserDetailsService {
	public PersistedUserDetails loadUserByUsername(String domain, String username) throws UsernameNotFoundException;
	public PersistedUserDetails findById(String id);
	public void saveUser(PersistedUserDetails persistedUser);
	public List<PersistedUserDetails> findByIds(List<String> fetchedUserIds);
}
