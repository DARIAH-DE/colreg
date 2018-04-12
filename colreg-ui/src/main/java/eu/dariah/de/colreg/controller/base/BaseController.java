package eu.dariah.de.colreg.controller.base;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;

import de.unibamberg.minf.core.web.controller.BaseTranslationController;
import eu.dariah.de.colreg.service.CollectionService;
import eu.dariah.de.dariahsp.model.web.AuthPojo;
import eu.dariah.de.dariahsp.web.AuthInfoHelper;

public abstract class BaseController extends BaseTranslationController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public final String NAVIGATION_ELEMENT_ATTRIBUTE = "_navigationAttribute";
	
	@Autowired private CollectionService collectionService;
	@Autowired protected AuthInfoHelper authInfoHelper;

	public BaseController(String mainNavId) {
		super(mainNavId);
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
