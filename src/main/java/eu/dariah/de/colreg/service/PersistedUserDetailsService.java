package eu.dariah.de.colreg.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import eu.dariah.de.colreg.model.PersistedUserDetails;

public interface PersistedUserDetailsService {
	public PersistedUserDetails loadUserByUsername(String domain, String username) throws UsernameNotFoundException;
	public void saveUser(PersistedUserDetails persistedUser);
}
