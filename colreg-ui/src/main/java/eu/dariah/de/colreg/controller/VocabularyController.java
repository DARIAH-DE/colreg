package eu.dariah.de.colreg.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.unibamberg.minf.core.web.localization.MessageSource;
import de.unibamberg.minf.core.web.pojo.MessagePojo;
import de.unibamberg.minf.core.web.pojo.ModelActionPojo;
import eu.dariah.de.colreg.model.vocabulary.UnitOfMeasurement;
import eu.dariah.de.colreg.service.VocabularyService;

@Controller
@RequestMapping("/vocabulary/")
public class VocabularyController {

	@Autowired private VocabularyService vocabularyService;
	@Autowired private MessageSource messageSource;
	
	@RequestMapping(value="{vocabularyId}/async/add", method=RequestMethod.GET)
	public @ResponseBody ModelActionPojo addVocabularyItem(@RequestParam String vocabularyId, @RequestParam String value, Locale locale) {
		if (vocabularyId.equals("uom")) {
			return this.addUom(value, locale);
		}		
		return null;
	}
	
	private ModelActionPojo addUom(String uom, Locale locale) {
		ModelActionPojo result = new ModelActionPojo();
		
		UnitOfMeasurement unit = vocabularyService.findUnitOfMeasurementByName(uom);
		if (unit!=null) {
			result.setMessage(new MessagePojo("error", "", 
					messageSource.getMessage("~eu.dariah.de.colreg.view.collection.notification.uom_exists_error", null, locale)));
		} else {
			unit = new UnitOfMeasurement();
			unit.setName(uom.trim());
			unit.setMessageCode(uom.replaceAll("\\s+",""));
			vocabularyService.saveUnitOfMeasurement(unit);
			result.setSuccess(true);
			result.setPojo(unit);
		}
		return result;
	}
}
