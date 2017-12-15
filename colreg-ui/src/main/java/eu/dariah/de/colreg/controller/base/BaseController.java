package eu.dariah.de.colreg.controller.base;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;

import eu.dariah.de.colreg.service.CollectionService;
import eu.dariah.de.dariahsp.model.web.AuthPojo;
import eu.dariah.de.dariahsp.web.AuthInfoHelper;

public class BaseController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public final String NAVIGATION_ELEMENT_ATTRIBUTE = "_navigationAttribute";
	
	@Autowired protected AuthInfoHelper authInfoHelper;
	@Autowired private CollectionService collectionService;
	
	@Value(value="${url.login:null}")
	private String loginUrl;
	
	@Value(value="${url.logout:null}")
	private String logoutUrl;
	
	@ModelAttribute("_loginUrl")
	public String getLoginUrl() {
		return loginUrl;
	}

	@ModelAttribute("_logoutUrl")
	public String getLogoutUrl() {
		return logoutUrl;
	}
	
	@ModelAttribute("_draftCount")
	public Long getDraftCount(HttpServletRequest request) {
		AuthPojo auth = authInfoHelper.getAuth(request);
		
		if (auth!=null && auth.isAuth()) {
			return collectionService.countDrafts(auth.getUserId()); 
		}
		
		return null;
	}
	
	

}
