package eu.dariah.de.colreg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.vocabulary.LicenseGroupDao;
import eu.dariah.de.colreg.model.vocabulary.License;
import eu.dariah.de.colreg.model.vocabulary.LicenseGroup;

@Service
public class LicenseServiceImpl implements LicenseService {
	@Autowired private LicenseGroupDao licenseGroupDao;
	
	@Override
	public List<License> findAllLicenses() {
		return licenseGroupDao.findAllLicenses();
	}

	@Override
	public License findLicenseById(String licenseId) {
		return licenseGroupDao.findLicenseById(licenseId);
	}

	@Override
	public List<LicenseGroup> findAllLicenseGroups() {
		return licenseGroupDao.findAll();
	}

	@Override
	public LicenseGroup findLicenseGroupById(String licenseGroupId) {
		return licenseGroupDao.findById(licenseGroupId);
	}
}