package eu.dariah.de.colreg.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.vocabulary.AccrualPolicyDao;
import eu.dariah.de.colreg.model.vocabulary.AccrualPolicy;

@Service
public class AccrualPolicyServiceImpl implements AccrualPolicyService {
	@Autowired private AccrualPolicyDao accrualPolicyDao;
	
	@Override
	public List<AccrualPolicy> findAllAccrualPolicies() {
		return accrualPolicyDao.findAll();
	}

	@Override
	public Map<String, String> findAccrualPolicyIdIdentifierMap() {
		Map<String, String> accrualPolicyIdIdentifierMap = new HashMap<String, String>();
		for (AccrualPolicy p : this.findAllAccrualPolicies()) {
			accrualPolicyIdIdentifierMap.put(p.getId(), p.getLabel());
		}
		return accrualPolicyIdIdentifierMap;
	}
}
