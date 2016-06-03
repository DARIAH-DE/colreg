package eu.dariah.de.colreg.dao.vocabulary;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.AccrualPeriodicity;


@Repository
public class AccrualPeriodicityDaoImpl extends BaseDaoImpl<AccrualPeriodicity> implements AccrualPeriodicityDao {
	public AccrualPeriodicityDaoImpl() {
		super(AccrualPeriodicity.class);
	}
}
