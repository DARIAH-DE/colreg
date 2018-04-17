package eu.dariah.de.colreg.pojo.converter;

import org.springframework.stereotype.Component;

import eu.dariah.de.colreg.pojo.api.ExtendedCollectionApiPojo;
import eu.dariah.de.colreg.pojo.converter.base.BaseCollectionApiConverter;

@Component
public class ExtendedCollectionApiConverter extends BaseCollectionApiConverter<ExtendedCollectionApiPojo> {

	@Override
	protected ExtendedCollectionApiPojo createPojo() {
		return new ExtendedCollectionApiPojo();
	}
	
}
