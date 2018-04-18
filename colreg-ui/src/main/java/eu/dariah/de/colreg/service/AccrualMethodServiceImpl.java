package eu.dariah.de.colreg.service;

import java.util.List;

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
}
