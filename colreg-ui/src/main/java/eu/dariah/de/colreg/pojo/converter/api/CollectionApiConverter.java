package eu.dariah.de.colreg.pojo.converter.api;

import org.springframework.stereotype.Component;

import eu.dariah.de.colreg.pojo.api.CollectionApiPojo;
import eu.dariah.de.colreg.pojo.converter.base.BaseCollectionApiConverter;

@Component
public class CollectionApiConverter extends BaseCollectionApiConverter<CollectionApiPojo> {
	@Override
	protected CollectionApiPojo createPojo() {
		return new CollectionApiPojo();
	}	
}
