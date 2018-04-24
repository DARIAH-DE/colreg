package eu.dariah.de.colreg.model.validation;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;


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
			this.innerValidate(clazz.cast(target), errors);
		}
	}
	
	protected javax.validation.Validator createValidator() {
		Configuration<?> config = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        factory.close();
		return validator;
	}
	
	protected void rejectValue(Errors errors, ConstraintViolation<?> violation, String formatPath, Object... args) {
		String messageCode, property;
		if (violation.getMessage().startsWith("{")) {
			messageCode = violation.getMessage().substring(1, violation.getMessage().toString().length()-1);
		} else {
			messageCode = violation.getMessage();
		}
		if (args==null || args.length==0) {
			property = formatPath;
		} else {
			property = String.format(formatPath, args);
		}
		errors.rejectValue(property, messageCode);
	}
		
	public abstract void innerValidate (T object, Errors errors);
}
