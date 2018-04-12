package eu.dariah.de.colreg.pojo.converter;

import java.util.Locale;

import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import eu.dariah.de.colreg.model.vocabulary.generic.VocabularyItem;
import eu.dariah.de.colreg.pojo.VocabularyItemPojo;
import eu.dariah.de.colreg.pojo.converter.base.BaseConverter;

@Component
public class VocabularyItemConverter extends BaseConverter<VocabularyItem, VocabularyItemPojo> {
	@Override
	public VocabularyItemPojo convertToPojo(VocabularyItem object, Locale locale) {
		VocabularyItemPojo pojo = new VocabularyItemPojo();
		pojo.setId(object.getId());
		pojo.setIdentifier(object.getIdentifier());
		pojo.setVocabularyId(object.getVocabularyIdentifier());
		pojo.setDefaultName(object.getDefaultName());		
		try {
			pojo.setLocalizedLabel(messageSource.getMessage(object.getMessageCode(), null, locale));
			pojo.setHasCurrentLocale(true);
		} catch (NoSuchMessageException e) {
			pojo.setLocalizedLabel("~" + object.getDefaultName());
			pojo.setHasCurrentLocale(false);
		}
		return pojo;
	}
}
