package eu.dariah.de.colreg.dao;

import org.springframework.stereotype.Repository;

import de.unibamberg.minf.dme.model.version.VersionInfo;
import eu.dariah.de.colreg.dao.base.BaseDaoImpl;

@Repository
public class VersionDaoImpl extends BaseDaoImpl<VersionInfo> implements VersionDao {
	public VersionDaoImpl() {
		super(VersionInfo.class);
	}
}
