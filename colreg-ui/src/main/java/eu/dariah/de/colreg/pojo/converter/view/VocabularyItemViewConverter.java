package eu.dariah.de.colreg.pojo.converter.view;

import java.util.Locale;

import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import eu.dariah.de.colreg.model.vocabulary.generic.VocabularyItem;
import eu.dariah.de.colreg.pojo.converter.base.BaseConverter;
import eu.dariah.de.colreg.pojo.view.VocabularyItemViewPojo;

@Component
public class VocabularyItemViewConverter extends BaseConverter<VocabularyItem, VocabularyItemViewPojo> {
	@Override
	public VocabularyItemViewPojo convertToPojo(VocabularyItem object, Locale locale) {
		VocabularyItemViewPojo pojo = new VocabularyItemViewPojo();
		pojo.setId(object.getId());
		pojo.setIdentifier(object.getIdentifier());
		pojo.setVocabularyId(object.getVocabularyIdentifier());
		pojo.setDefaultName(object.getDefaultName());	
		pojo.setDeleted(object.isDeleted());
		pojo.setExternalIdentifier(object.getExternalIdentifier());
		if (locale!=null) {
			try {
				pojo.setLocalizedLabel(messageSource.getMessage(object.getMessageCode(), null, locale));
				pojo.setHasCurrentLocale(true);
			} catch (NoSuchMessageException e) {
				pojo.setHasCurrentLocale(false);
			}
		}
		return pojo;
	}
}
