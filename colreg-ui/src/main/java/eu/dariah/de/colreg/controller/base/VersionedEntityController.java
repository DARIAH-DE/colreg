package eu.dariah.de.colreg.controller.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;

import eu.dariah.de.colreg.model.base.VersionedEntityImpl;
import eu.dariah.de.colreg.pojo.VocabularyPojo;
import eu.dariah.de.colreg.pojo.converter.VocabularyConverter;
import eu.dariah.de.colreg.service.UserServiceImpl;
import eu.dariah.de.colreg.service.VocabularyService;
import eu.dariah.de.dariahsp.model.User;

public class VersionedEntityController extends BaseController {
	@Autowired protected UserServiceImpl userService;
	
	@Autowired protected VocabularyService vocabularyService;	
	@Autowired protected VocabularyConverter vocabularyConverter;
	
	@ModelAttribute("_vocabularies")
	public List<VocabularyPojo> getVocabularies(Locale locale) {
		return vocabularyConverter.convertToPojos(vocabularyService.findVocabularies(), locale);
	}
	
	public VersionedEntityController(String mainNavId) {
		super(mainNavId);
	}
	
	protected <T extends VersionedEntityImpl> void setUsers(List<T> ves) {
		if (ves==null || ves.size()==0) {
			return;
		}
		List<String> fetchedUserIds = new ArrayList<String>();
		for (VersionedEntityImpl ve : ves) {
			if (ve.getVersionCreator()!=null && !fetchedUserIds.contains(ve.getVersionCreator())) {
				fetchedUserIds.add(ve.getVersionCreator());
			}
			if (ve.getEntityCreator()!=null && !fetchedUserIds.contains(ve.getEntityCreator())) {
				fetchedUserIds.add(ve.getEntityCreator());
			}
		}
		
		List<User> uds = userService.findByIds(fetchedUserIds);
		if (uds==null || uds.size()==0) {
			return;
		}
		for (VersionedEntityImpl ve : ves) {
			if (ve.getVersionCreator()!=null && fetchedUserIds.contains(ve.getVersionCreator())) {
				for (User ud : uds) {
					if (ud.getId().equals(ve.getVersionCreator())) {
						ve.setVersionCreator(ud.getUsername());
					}
				}
			}
			if (ve.getEntityCreator()!=null && fetchedUserIds.contains(ve.getEntityCreator())) {
				for (User ud : uds) {
					if (ud.getId().equals(ve.getEntityCreator())) {
						ve.setEntityCreator(ud.getUsername());
					}
				}
			}
		}
	}
	
	protected void setUsers(VersionedEntityImpl ve) {
		if (ve==null) {
			return;
		}
		UserDetails ud;
		if (ve.getVersionCreator()!=null) {
			ud = userService.findById(ve.getVersionCreator());
			if (ud!=null) {
				ve.setVersionCreator(ud.getUsername());
			}
		}
		if (ve.getEntityCreator()!=null) {
			ud = userService.findById(ve.getEntityCreator());
			if (ud!=null) {
				ve.setEntityCreator(ud.getUsername());
			}
		}
	}
}
