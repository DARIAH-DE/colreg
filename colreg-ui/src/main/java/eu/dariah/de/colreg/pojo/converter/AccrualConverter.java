package eu.dariah.de.colreg.pojo.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;

import eu.dariah.de.colreg.model.Accrual;
import eu.dariah.de.colreg.pojo.AccrualPojo;
import eu.dariah.de.colreg.pojo.converter.base.BaseConverter;

@Component
public class AccrualConverter extends BaseConverter<Accrual, AccrualPojo> {

	@Override
	public AccrualPojo convertToPojo(Accrual accrual, Locale locale) {
		return this.convertToPojo(accrual, locale, null, null, null);
	}
	
	public List<AccrualPojo> convertToPojos(List<Accrual> accruals, Locale locale, Map<String, String> accrualMethodIdIdentifierMap, 
			Map<String, String> accrualPolicyIdIdentifierMap, Map<String, String> accrualPeriodicityIdIdentifierMap) {
		if (accruals==null || accruals.isEmpty()) {
			return new ArrayList<AccrualPojo>(0);
		}
		List<AccrualPojo> pojos = new ArrayList<AccrualPojo>(accruals.size());
		for (Accrual accrual : accruals) {
			pojos.add(this.convertToPojo(accrual, locale, accrualMethodIdIdentifierMap, accrualPolicyIdIdentifierMap, accrualPeriodicityIdIdentifierMap));
		}
		return pojos;
	}
	
	public AccrualPojo convertToPojo(Accrual accrual, Locale locale, Map<String, String> accrualMethodIdIdentifierMap, 
			Map<String, String> accrualPolicyIdIdentifierMap, Map<String, String> accrualPeriodicityIdIdentifierMap) {
		AccrualPojo pojo = new AccrualPojo();
		
		if (accrualMethodIdIdentifierMap!=null) {
			pojo.setAccrualMethod(accrualMethodIdIdentifierMap.get(accrual.getAccrualMethod()));
		}
		if (accrualPolicyIdIdentifierMap!=null) {
			pojo.setAccrualPolicy(accrualPolicyIdIdentifierMap.get(accrual.getAccrualPolicy()));
		}
		if (accrualPeriodicityIdIdentifierMap!=null) {
			pojo.setAccrualPeriodicity(accrualPeriodicityIdIdentifierMap.get(accrual.getAccrualPeriodicity()));
		}
		return pojo;
	}
}
