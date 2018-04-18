package eu.dariah.de.colreg.pojo.converter.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public abstract class BaseConverter<TObj, TPojo> {
	private static final DateTimeFormatter DISPLAY_TIMESTAMP_FORMATTER = DateTimeFormat.forStyle("LM");
	
	@Autowired protected MessageSource messageSource;
	
	public List<TPojo> convertToPojos(List<TObj> objects, Locale locale) {
		if (objects==null || objects.isEmpty()) {
			return new ArrayList<TPojo>(0);
		}
		List<TPojo> pojos = new ArrayList<TPojo>(objects.size());
		for (TObj object : objects) {
			pojos.add(this.convertToPojo(object, locale));
		}
		return pojos;
	}
	
	protected String getDisplayTimestamp(DateTime timestamp, Locale locale) {
		if (locale==null) {
			return null;
		}
		return DISPLAY_TIMESTAMP_FORMATTER.withLocale(locale).print(timestamp);
	}

	public abstract TPojo convertToPojo(TObj object, Locale locale);
}
