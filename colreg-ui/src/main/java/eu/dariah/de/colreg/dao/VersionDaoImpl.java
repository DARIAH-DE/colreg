package eu.dariah.de.colreg.dao;

import de.unibamberg.minf.dme.model.version.VersionInfo;
import eu.dariah.de.colreg.dao.base.BaseDaoImpl;

public class VersionDaoImpl extends BaseDaoImpl<VersionInfo> implements VersionDao {
	public VersionDaoImpl() {
		super(VersionInfo.class);
	}
}
