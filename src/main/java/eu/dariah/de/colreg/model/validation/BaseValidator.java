package eu.dariah.de.colreg.model.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import eu.dariah.de.colreg.model.base.BaseIdentifiable;

public abstract class BaseValidator<T extends BaseIdentifiable> implements Validator, ValidationPreprocessor<T> {
	protected final Class<T> clazz;
	
	@Autowired private LocalValidatorFactoryBean validator;
	
	public BaseValidator(Class<T> clazz) {
		this.clazz = (Class<T>)clazz;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return this.clazz.equals(clazz);
	}

	@Override
	public final void validate(Object target, Errors errors) {
		if (target!=null) {
			T object = clazz.cast(target);
			this.preprocess(object);
			validator.validate(target, errors);
			this.validate(clazz.cast(target), errors);
		}
	}
	
	public abstract void validate (T object, Errors errors);
}
