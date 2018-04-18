package eu.dariah.de.colreg.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.vocabulary.AccrualMethodDao;
import eu.dariah.de.colreg.model.vocabulary.AccrualMethod;

@Service
public class AccrualMethodServiceImpl implements AccrualMethodService {
	@Autowired private AccrualMethodDao accrualMethodDao;
	
	@Override
	public List<AccrualMethod> findAllAccrualMethods() {
		return accrualMethodDao.findAll();
	}

	@Override
	public Map<String, String> findAccrualMethodIdIdentifierMap() {
		Map<String, String> accrualMethodIdIdentifierMap = new HashMap<String, String>();
		for (AccrualMethod m : this.findAllAccrualMethods()) {
			accrualMethodIdIdentifierMap.put(m.getId(), m.getLabel());
		}
		return accrualMethodIdIdentifierMap;
	}
}
