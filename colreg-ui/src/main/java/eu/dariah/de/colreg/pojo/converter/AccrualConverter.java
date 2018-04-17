package eu.dariah.de.colreg.pojo.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import eu.dariah.de.colreg.model.Accrual;
import eu.dariah.de.colreg.model.vocabulary.AccrualMethod;
import eu.dariah.de.colreg.model.vocabulary.AccrualPeriodicity;
import eu.dariah.de.colreg.model.vocabulary.AccrualPolicy;
import eu.dariah.de.colreg.pojo.AccrualPojo;
import eu.dariah.de.colreg.pojo.converter.base.BaseConverter;

public class AccrualConverter extends BaseConverter<Accrual, AccrualPojo> {

	@Override
	public AccrualPojo convertToPojo(Accrual object, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * private List<AccrualPojo> convertAccrualToPojos(List<Accrual> accrualMethods) {
		if (accrualMethods==null || accrualMethods.size()==0) {
			return null;
		}
		List<AccrualPojo> accrualPojos = new ArrayList<AccrualPojo>();
		AccrualPojo accPojo;
		AccrualMethod accMethod;
		AccrualPolicy accPolicy;
		AccrualPeriodicity accPeriodicity;
		
		for (Accrual acc : accrualMethods) {
			accPojo = new AccrualPojo();
			
			accMethod = accMethodDao.findById(acc.getAccrualMethod());
			if (accMethod!=null) {
				accPojo.setAccrualMethod(accMethod.getIdentifier());
			}
			accPolicy = accPolicyDao.findById(acc.getAccrualPolicy());
			if (accPolicy!=null) {
				accPojo.setAccrualPolicy(accPolicy.getIdentifier());
			}
			accPeriodicity = accPeriodicityDao.findById(acc.getAccrualPeriodicity());
			if (accPeriodicity!=null) {
				accPojo.setAccrualPeriodicity(accPeriodicity.getIdentifier());	
			}
			accrualPojos.add(accPojo);
		}
		
		return accrualPojos;
	}
	 */
}
