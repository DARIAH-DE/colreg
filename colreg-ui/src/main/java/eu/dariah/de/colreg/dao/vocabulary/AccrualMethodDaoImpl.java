package eu.dariah.de.colreg.dao.vocabulary;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.AccrualMethod;

@Repository
public class AccrualMethodDaoImpl extends BaseDaoImpl<AccrualMethod> implements AccrualMethodDao {
	public AccrualMethodDaoImpl() {
		super(AccrualMethod.class);
	}
}
