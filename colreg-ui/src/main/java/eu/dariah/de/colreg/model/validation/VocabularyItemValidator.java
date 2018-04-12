package eu.dariah.de.colreg.model.validation;

import java.util.List;

import javax.validation.ConstraintValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import de.unibamberg.minf.core.web.validation.AbstractValidator;
import eu.dariah.de.colreg.model.vocabulary.generic.VocabularyItem;
import eu.dariah.de.colreg.service.VocabularyItemService;

@Component
public class VocabularyItemValidator extends AbstractValidator {

	@Autowired private VocabularyItemService vocabularyItemService;
	
	@Override
	public boolean supports(Class<?> c) {
		return VocabularyItem.class.equals(c);
	}

	@Override
	protected void addExtraValidation(Object objectForm, Errors errors) {
		VocabularyItem vocabularyItem = (VocabularyItem)objectForm;

		List<VocabularyItem> items = vocabularyItemService.findVocabularyItemByIdentifier(vocabularyItem.getVocabularyId(), vocabularyItem.getIdentifier());
		if (items.size()>1 || (items.size()==1 && !items.get(0).getId().equals(vocabularyItem.getId()))) {
			errors.rejectValue("identifier", "~eu.dariah.de.colreg.validation.vocabulary_item.identifier_not_unique");
		}
	}

	@Override
	public void releaseInstance(ConstraintValidator<?, ?> instance) {}
}
