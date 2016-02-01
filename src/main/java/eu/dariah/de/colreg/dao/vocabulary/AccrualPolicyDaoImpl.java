package eu.dariah.de.colreg.dao.vocabulary;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.AccrualPolicy;

public class AccrualPolicyDaoImpl extends BaseDaoImpl<AccrualPolicy> implements AccrualPolicyDao {
	public AccrualPolicyDaoImpl() {
		super(AccrualPolicy.class);
	}
}
