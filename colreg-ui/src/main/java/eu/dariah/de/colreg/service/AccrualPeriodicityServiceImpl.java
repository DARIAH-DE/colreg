package eu.dariah.de.colreg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.vocabulary.AccrualPeriodicityDao;
import eu.dariah.de.colreg.model.vocabulary.AccrualPeriodicity;

@Service
public class AccrualPeriodicityServiceImpl implements AccrualPeriodicityService {
	@Autowired private AccrualPeriodicityDao accrualPeriodicityDao;
	
	@Override
	public List<AccrualPeriodicity> findAllAccrualPeriodicities() {
		return accrualPeriodicityDao.findAll();
	}
}
