package eu.dariah.de.colreg.dao.vocabulary;

import java.util.List;

import eu.dariah.de.colreg.dao.base.BaseDao;
import eu.dariah.de.colreg.model.vocabulary.License;
import eu.dariah.de.colreg.model.vocabulary.LicenseGroup;

public interface LicenseGroupDao extends BaseDao<LicenseGroup> {
	public List<License> findAllLicenses();
	public License findLicenseById(String licenseId);
}
