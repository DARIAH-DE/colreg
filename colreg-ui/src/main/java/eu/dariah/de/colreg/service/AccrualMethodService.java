package eu.dariah.de.colreg.service;

import java.util.List;
import java.util.Map;

import eu.dariah.de.colreg.model.vocabulary.AccrualMethod;

public interface AccrualMethodService {
	public List<AccrualMethod> findAllAccrualMethods();

	public Map<String, String> findAccrualMethodIdIdentifierMap();
}
