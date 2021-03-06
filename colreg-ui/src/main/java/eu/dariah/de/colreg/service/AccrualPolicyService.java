package eu.dariah.de.colreg.service;

import java.util.List;
import java.util.Map;

import eu.dariah.de.colreg.model.vocabulary.AccrualPolicy;

public interface AccrualPolicyService {
	public List<AccrualPolicy> findAllAccrualPolicies();

	public Map<String, String> findAccrualPolicyIdIdentifierMap();
}
