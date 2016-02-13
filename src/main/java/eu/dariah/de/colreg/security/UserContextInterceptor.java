package eu.dariah.de.colreg.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import eu.dariah.de.colreg.model.PersistedUserDetails;
import eu.dariah.de.colreg.service.CollectionService;

public class UserContextInterceptor extends HandlerInterceptorAdapter {
	@Autowired private CollectionService collectionService;
	
	@Value(value="${url.login:null}")
	private String loginUrl;
	
	@Value(value="${url.logout:null}")
	private String logoutUrl;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PersistedUserDetails user = null;
		HttpSession session = request.getSession();
		
		if (auth != null && auth.getDetails() instanceof PersistedUserDetails) {
			user = (PersistedUserDetails) auth.getDetails();
			
			int draftCount = collectionService.findAllDrafts(user.getId()).size();
			session.setAttribute("_draftCount", draftCount);
		}
		session.setAttribute("_loginUrl", loginUrl);
		session.setAttribute("_logoutUrl", logoutUrl);
		return super.preHandle(request, response, handler);
	}
}
