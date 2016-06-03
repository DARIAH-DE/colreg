package eu.dariah.de.colreg.dao.vocabulary;

import org.springframework.stereotype.Repository;

import eu.dariah.de.colreg.dao.base.BaseDaoImpl;
import eu.dariah.de.colreg.model.vocabulary.AccrualPolicy;

@Repository
public class AccrualPolicyDaoImpl extends BaseDaoImpl<AccrualPolicy> implements AccrualPolicyDao {
	public AccrualPolicyDaoImpl() {
		super(AccrualPolicy.class);
	}
}
