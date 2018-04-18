package eu.dariah.de.colreg.service;

import java.util.List;

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
}
