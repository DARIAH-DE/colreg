package eu.dariah.de.colreg.pojo.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;

import eu.dariah.de.colreg.model.Access;
import eu.dariah.de.colreg.pojo.AccessPojo;
import eu.dariah.de.colreg.pojo.converter.base.BaseConverter;

@Component
public class AccessConverter extends BaseConverter<Access, AccessPojo> {

	@Override
	public AccessPojo convertToPojo(Access access, Locale locale) {
		return this.convertToPojo(access, locale, null);
	}
	
	public List<AccessPojo> convertToPojos(List<Access> access, Locale locale, Map<String, String> accessTypeIdLabelMap) {
		if (access==null || access.isEmpty()) {
			return new ArrayList<AccessPojo>(0);
		}
		List<AccessPojo> pojos = new ArrayList<AccessPojo>(access.size());
		for (Access a : access) {
			pojos.add(this.convertToPojo(a, locale, accessTypeIdLabelMap));
		}
		return pojos;
	}
	
	public AccessPojo convertToPojo(Access access, Locale locale, Map<String, String> accessTypeIdLabelMap) {
		AccessPojo pojo = null;
		if (access!=null) {
			pojo = new AccessPojo();
			if (access.getSchemeIds()!=null) {
				pojo.setSchemeIds(new ArrayList<String>(access.getSchemeIds()));
			}
			pojo.setSet(access.getOaiSet());
			if (accessTypeIdLabelMap!=null) {
				pojo.setType(accessTypeIdLabelMap.get(access.getType()));
			}
			pojo.setUri(access.getUri());
		}
		return pojo;
	}
}
