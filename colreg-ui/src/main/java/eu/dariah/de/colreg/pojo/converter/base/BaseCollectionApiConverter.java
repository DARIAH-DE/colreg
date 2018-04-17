package eu.dariah.de.colreg.pojo.converter.base;

import java.util.Locale;

import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.pojo.api.CollectionApiPojo;

public abstract class BaseCollectionApiConverter<TPojo extends CollectionApiPojo> extends BaseConverter<Collection, TPojo> {

	@Override
	public TPojo convertToPojo(Collection object, Locale locale) {
		TPojo pojo = this.createPojo();
		
		
		return pojo;
	}
	
	protected abstract TPojo createPojo();

}
