package eu.dariah.de.colreg.controller.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
}
