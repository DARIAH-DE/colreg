package eu.dariah.de.colreg.model.validation;

import eu.dariah.de.colreg.model.base.BaseIdentifiable;

public interface ValidationPreprocessor<T extends BaseIdentifiable> {
	public void preprocess(T object);
}
