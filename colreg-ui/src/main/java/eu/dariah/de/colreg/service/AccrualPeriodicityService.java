package eu.dariah.de.colreg.service;

import java.util.List;
import java.util.Map;

import eu.dariah.de.colreg.model.vocabulary.AccrualPeriodicity;

public interface AccrualPeriodicityService {
	public List<AccrualPeriodicity> findAllAccrualPeriodicities();

	public Map<String, String> findAccrualPeriodicityIdIdentifierMap();
}
