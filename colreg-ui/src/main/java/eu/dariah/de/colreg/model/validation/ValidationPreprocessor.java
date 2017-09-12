package eu.dariah.de.colreg.model.validation;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public interface ValidationPreprocessor<T extends BaseIdentifiable> {
	public void preprocess(T object);
}
