package eu.dariah.de.colreg.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	public Map<String, String> findAccrualPeriodicityIdIdentifierMap() {
		Map<String, String> accrualPeriodicityIdIdentifierMap = new HashMap<String, String>();
		for (AccrualPeriodicity p : this.findAllAccrualPeriodicities()) {
			accrualPeriodicityIdIdentifierMap.put(p.getId(), p.getLabel());
		}
		return accrualPeriodicityIdIdentifierMap;
	}
}
