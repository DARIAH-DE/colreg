package eu.dariah.de.colreg.pojo.converter.api;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import de.unibamberg.minf.core.web.init.LocaleAwareInitializationService;
import eu.dariah.de.colreg.model.vocabulary.generic.VocabularyItem;
import eu.dariah.de.colreg.pojo.api.VocabularyItemApiPojo;
import eu.dariah.de.colreg.pojo.converter.base.BaseConverter;

@Component
public class VocabularyItemApiConverter extends BaseConverter<VocabularyItem, VocabularyItemApiPojo> {
	
	@Autowired private LocaleAwareInitializationService initService;
	
	@Override
	public VocabularyItemApiPojo convertToPojo(VocabularyItem object, Locale locale) {
		VocabularyItemApiPojo pojo = new VocabularyItemApiPojo();
		pojo.setId(object.getId());
		pojo.setIdentifier(object.getIdentifier());
		pojo.setVocabularyId(object.getVocabularyIdentifier());
		pojo.setDefaultName(object.getDefaultName());	
		pojo.setDeleted(object.isDeleted());
		pojo.setExternalIdentifier(object.getExternalIdentifier());
		if (locale!=null) {
			try {
				pojo.setLocalizedLabel(messageSource.getMessage(object.getMessageCode(), null, locale));
			} catch (NoSuchMessageException e) {
				pojo.setLocalizedLabel("");
			}
		} else {
			Map<String, String> messageMap = new HashMap<String, String>();
			String message;
			for (String localeCode : initService.getLocaleCodes()) {
				try {
					message = messageSource.getMessage(object.getMessageCode(), null, new Locale(localeCode));
				} catch (NoSuchMessageException e) {
					message = null;
				}
				messageMap.put(localeCode, message);
			}
			pojo.setLabels(messageMap);
		}
		return pojo;
	}
}
