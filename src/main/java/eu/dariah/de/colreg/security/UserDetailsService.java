package eu.dariah.de.colreg.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.NameID;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

import de.dariah.aai.javasp.base.Role;
import de.dariah.aai.javasp.exception.UserCredentialsException;
import de.dariah.aai.javasp.exception.UserCredentialsException.UserCredentialsExceptionTypes;
import eu.dariah.de.colreg.model.PersistedUserDetails;
import eu.dariah.de.colreg.model.RoleImpl;
import eu.dariah.de.colreg.service.PersistedUserDetailsService;

public class UserDetailsService implements SAMLUserDetailsService {
	protected static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);
	
	protected static final String ID_ATTR_NAME = "urn:oid:1.3.6.1.4.1.5923.1.1.1.6";
	
	@Autowired private PersistedUserDetailsService persistedUserDetailsService;
	
	@Override
	public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {

		PersistedUserDetails userDetails = null;
		try {
			userDetails = getUserDetails(credential);
			PersistedUserDetails savedUser = persistedUserDetailsService.loadUserByUsername(userDetails.getEndpointId(), userDetails.getUsername());
			if (savedUser!=null) {
				savedUser.setAuthorities(userDetails.getAuthorities());
				userDetails = savedUser;
			}
			
			userDetails.setLastLogin(DateTime.now());
			persistedUserDetailsService.saveUser(userDetails);
			
			
			
			logger.info("Authenticated user: " + userDetails.getUsername());
			
		} catch (Exception e) {
			throw new UsernameNotFoundException("Failed to process user details", e);
		}
		
		return userDetails;
	}
	
	private PersistedUserDetails getUserDetails(SAMLCredential credential) throws Exception {
		NameID nameId = credential.getNameID();
		
		String fetchNameId = null;
		String fetchEndpoint = null;
		
		Collection<Role> roles = new ArrayList<Role>();
		
		for (Attribute attr : credential.getAttributes()) {
			if (attr.getName().toLowerCase().equals(ID_ATTR_NAME.toLowerCase())) {
				fetchNameId = getDistinctValue(attr.getAttributeValues());
			} else if (attr.getName().toLowerCase().equals("urn:oid:1.3.6.1.4.1.5923.1.5.1.1")) {
				
				if (attr.getAttributeValues()!=null && attr.getAttributeValues().size()>0) {
					for (XMLObject a : attr.getAttributeValues()) {
						if (a instanceof XSString) {
							RoleImpl r = new RoleImpl();
							r.setAuthority(((XSString)a).getValue().trim().toLowerCase());
							roles.add(r);
						}
					}
				}
			}
		}
		if (fetchNameId==null) {
			if (nameId.getValue() != null && !nameId.getValue().isEmpty()) {
				fetchNameId = nameId.getValue();
			} else {
				logger.error("Reuse of subject-NameID in configured but not provided for AttributeQuery generation");
				throw new UserCredentialsException(UserCredentialsExceptionTypes.NAME_ID_NOT_PROVIDED, "Reuse of subject-NameID in configured but not provided for AttributeQuery generation");
			}
		}
		
		if (nameId.getNameQualifier()!=null && !nameId.getNameQualifier().isEmpty()) {
			fetchEndpoint = nameId.getNameQualifier();
		} else {
			logger.error("No reidentifiable entityId available for user's home IDP");
			throw new UserCredentialsException(UserCredentialsExceptionTypes.ID_ATTRIBUTE_NOT_PROVIDED, "No reidentifiable entityId available for user's home IDP");
		}
		
		PersistedUserDetails ud = new PersistedUserDetails();
		ud.setEndpointId(fetchEndpoint);
		ud.setEndpointName(fetchEndpoint);
		ud.setUsername(fetchNameId.toLowerCase());
		ud.setHasAllAttributes(true);
		ud.setAuthorities(roles);
		
		return ud;
	}

	
	private String getDistinctValue(List<XMLObject> attrValues) throws Exception {
		
		if (attrValues.size() != 1) {
			throw new Exception("Unexpected size of attribute value array while reading user details; expected: 1, received: " + attrValues.size());
		}
		
		XMLObject attrValue = attrValues.get(0);
		
		if (attrValue instanceof XSString) {
			return ((XSString)attrValue).getValue();
		}
			
		throw new Exception("Unexpected value type; Expected XSString, received: " + attrValue.getClass().toString());
	}
}
