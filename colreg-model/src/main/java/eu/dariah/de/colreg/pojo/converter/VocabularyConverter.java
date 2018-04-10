package eu.dariah.de.colreg.pojo.converter;

import java.util.Locale;

import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import eu.dariah.de.colreg.model.vocabulary.generic.Vocabulary;
import eu.dariah.de.colreg.pojo.VocabularyPojo;
import eu.dariah.de.colreg.pojo.converter.base.BaseConverter;

@Component
public class VocabularyConverter extends BaseConverter<Vocabulary, VocabularyPojo> {

	@Override
	public VocabularyPojo convertToPojo(Vocabulary object, Locale locale) {
		
		VocabularyPojo pojo = new VocabularyPojo();
		pojo.setId(object.getId());
		pojo.setIdentifier(object.getIdentifier());
		
		
		try {
			pojo.setLocalizedLabel(messageSource.getMessage(object.getMessageCode(), null, locale));
		} catch (NoSuchMessageException e) {
			pojo.setLocalizedLabel("~" + object.getDefaultName());
		}
		
		
		
		
		return pojo;
	}


}
