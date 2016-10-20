package eu.dariah.de.colreg.service;

import java.util.List;

import eu.dariah.de.colreg.model.vocabulary.License;
import eu.dariah.de.colreg.model.vocabulary.LicenseGroup;

public interface LicenseService {
	public List<License> findAllLicenses();
	public License findLicenseById(String licenseId);
	
	public List<LicenseGroup> findAllLicenseGroups();
	public LicenseGroup findLicenseGroupById(String licenseGroupId);
}