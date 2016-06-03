package eu.dariah.de.colreg.controller.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import eu.dariah.de.colreg.model.PersistedUserDetails;
import eu.dariah.de.colreg.model.base.VersionedEntityImpl;
import eu.dariah.de.colreg.service.PersistedUserDetailsService;

public class VersionedEntityController extends BaseController {
	@Autowired protected PersistedUserDetailsService userDetailsService;
	
	
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
		
		List<PersistedUserDetails> uds = userDetailsService.findByIds(fetchedUserIds);
		if (uds==null || uds.size()==0) {
			return;
		}
		for (VersionedEntityImpl ve : ves) {
			if (ve.getVersionCreator()!=null && fetchedUserIds.contains(ve.getVersionCreator())) {
				for (PersistedUserDetails ud : uds) {
					if (ud.getId().equals(ve.getVersionCreator())) {
						ve.setVersionCreator(ud.getUsername());
					}
				}
			}
			if (ve.getEntityCreator()!=null && fetchedUserIds.contains(ve.getEntityCreator())) {
				for (PersistedUserDetails ud : uds) {
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
			ud = userDetailsService.findById(ve.getVersionCreator());
			if (ud!=null) {
				ve.setVersionCreator(ud.getUsername());
			}
		}
		if (ve.getEntityCreator()!=null) {
			ud = userDetailsService.findById(ve.getEntityCreator());
			if (ud!=null) {
				ve.setEntityCreator(ud.getUsername());
			}
		}
	}
}
