package eu.dariah.de.colreg.pojo.converter.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public abstract class BaseConverter<TObj extends BaseIdentifiable, TPojo extends BaseIdentifiable> {

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

	public abstract TPojo convertToPojo(TObj object, Locale locale);
}
