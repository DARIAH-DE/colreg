package eu.dariah.de.colreg.security;

import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import de.dariah.aai.javasp.base.Role;
import de.dariah.aai.javasp.base.SimpleUserDetails;
import eu.dariah.de.colreg.model.PersistedUserDetails;
import eu.dariah.de.colreg.model.RoleImpl;
import eu.dariah.de.colreg.service.PersistedUserDetailsService;

public class LocalAuthenticationProvider implements AuthenticationProvider {
	@Autowired private PersistedUserDetailsService persistedUserDetailsService;
	private UserDetailsService localUserDetailsService;

	public UserDetailsService getLocalUserDetailsService() { return localUserDetailsService; }
 	public void setLocalUserDetailsService(UserDetailsService localUserDetailsService) { this.localUserDetailsService = localUserDetailsService; }
 	

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			UserDetails user = localUserDetailsService.loadUserByUsername(authentication.getName());
			if (user.getPassword().equals(authentication.getCredentials())) {
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword().hashCode(), user.getAuthorities());
				auth.setDetails(this.getUserDetails(user));
				return auth;
			} else {
				throw new BadCredentialsException("Wrong password");
			}
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Provided username and/or password wrong.");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
	}
	
	private SimpleUserDetails getUserDetails(UserDetails user) {
		PersistedUserDetails persistedUser = persistedUserDetailsService.loadUserByUsername("LOCAL", user.getUsername());
		if (persistedUser==null) {
			persistedUser = new PersistedUserDetails();
			persistedUser.setEndpointId("LOCAL");
			persistedUser.setEndpointName("LOCAL");
			persistedUser.setHasAllAttributes(true);
			persistedUser.setUsername(user.getUsername());
		}
		Collection<Role> roles = new ArrayList<Role>();
		for (GrantedAuthority r : user.getAuthorities()) {
			roles.add(new RoleImpl(r.getAuthority()));
		}						
		persistedUser.setAuthorities(roles);		
		persistedUser.setExpired(!(user.isAccountNonExpired() && user.isAccountNonLocked() && user.isCredentialsNonExpired() && user.isEnabled()));
		persistedUser.setLastLogin(DateTime.now());
		
		persistedUserDetailsService.saveUser(persistedUser);
		
		return persistedUser;
	}
}
