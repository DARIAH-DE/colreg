package eu.dariah.de.colreg.controller.base;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.dariah.de.colreg.pojo.api.ApiResultPojo;

public class BaseApiController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected Locale handleLocale(ApiResultPojo<?> result, String locale) {
		if (locale!=null && !locale.isEmpty()) {
			Locale l = new Locale(locale);
						
			String country = null;
			String language = null;
			try {
				country = l.getISO3Country();
				language = l.getISO3Language();
			} catch (Exception e) { }
			
			if (country==null || language==null) {
				result.setMessage("Invalid locale specified. Must be ISO 639 alpha-2 or alpha-3 language code.");
				result.setSuccess(false);
				return null;
			} else {
				result.setSuccess(true);
				return l;
			}
		}
		// No locale specified...ok
		result.setSuccess(true);
		return null;
	}
}
