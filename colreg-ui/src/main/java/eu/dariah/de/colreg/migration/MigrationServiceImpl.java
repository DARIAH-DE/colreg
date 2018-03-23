package eu.dariah.de.colreg.migration;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.unibamberg.minf.dme.model.version.VersionInfo;
import eu.dariah.de.colreg.dao.VersionDao;

public class MigrationServiceImpl implements MigrationService {

	@Autowired private VersionDao versionDao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		List<VersionInfo> versions = versionDao.findAll();
		Collections.sort(versions);
		
	}

}
