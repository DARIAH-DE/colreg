package eu.dariah.de.colreg.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.unibamberg.minf.core.web.controller.ResourceNotFoundException;
import de.unibamberg.minf.core.web.localization.MessageSource;
import de.unibamberg.minf.core.web.pojo.MessagePojo;
import de.unibamberg.minf.core.web.pojo.ModelActionPojo;
import eu.dariah.de.colreg.controller.base.VersionedEntityController;
import eu.dariah.de.colreg.model.vocabulary.UnitOfMeasurement;
import eu.dariah.de.colreg.model.vocabulary.generic.Vocabulary;
import eu.dariah.de.colreg.model.vocabulary.generic.VocabularyItem;
import eu.dariah.de.colreg.pojo.TableListPojo;
import eu.dariah.de.colreg.pojo.VocabularyItemPojo;
import eu.dariah.de.colreg.pojo.VocabularyPojo;
import eu.dariah.de.colreg.pojo.converter.VocabularyConverter;
import eu.dariah.de.colreg.pojo.converter.VocabularyItemConverter;
import eu.dariah.de.colreg.service.VocabularyItemService;
import eu.dariah.de.colreg.service.VocabularyService;
import eu.dariah.de.dariahsp.model.web.AuthPojo;

@Controller
@RequestMapping("/vocabulary/")
public class VocabularyController extends VersionedEntityController {
	@Autowired protected VocabularyService vocabularyService;	
	@Autowired protected VocabularyConverter vocabularyConverter;
	
	@Autowired protected VocabularyItemService vocabularyItemService;
	@Autowired protected VocabularyItemConverter vocabularyItemConverter;
	
	@Autowired private MessageSource messageSource;
	
	@RequestMapping(value="{vocabularyId}/async/add", method=RequestMethod.GET)
	public @ResponseBody ModelActionPojo addVocabularyItem(@PathVariable String vocabularyId, @RequestParam String value, Locale locale) {
		if (vocabularyId.equals("uom")) {
			return this.addUom(value, locale);
		}		
		return null;
	}
	
	@RequestMapping(value="{vocabularyId}/", method=RequestMethod.GET)
	public String getList(@PathVariable String vocabularyId, Model model, Locale locale) {
		
		Vocabulary v = vocabularyService.findVocabulary(vocabularyId);
		model.addAttribute("vocabulary", vocabularyConverter.convertToPojo(v, locale));
		
		
		return "vocabulary/list";
	}
	
	@RequestMapping(value="{vocabularyId}/list", method=RequestMethod.GET)
	public @ResponseBody TableListPojo<VocabularyItemPojo> getAllVocabularyItems(@PathVariable String vocabularyId, Model model, Locale locale, HttpServletRequest request) {
		List<VocabularyItem> vocabularyItems = vocabularyItemService.findVocabularyItems(vocabularyId);
		List<VocabularyItemPojo> vocabularyItemPojos = vocabularyItemConverter.convertToPojos(vocabularyItems, locale);

		return new TableListPojo<VocabularyItemPojo>(vocabularyItemPojos);
	}
	
	@RequestMapping(value="{vocabularyId}/{vocabularyItemId}", method=RequestMethod.GET)
	public String getAddItemForm(@PathVariable String vocabularyId, @PathVariable String vocabularyItemId, Model model, Locale locale, HttpServletRequest request) {
		AuthPojo auth = authInfoHelper.getAuth(request);
		VocabularyItem vi;
		
		if (vocabularyItemId.toLowerCase().equals("new")) {
			if (!auth.isAuth()) {
				return "redirect:/" + this.getLoginUrl();
			}
			vi = vocabularyItemService.createVocabularyItem(vocabularyId);
		} else {
			vi = vocabularyItemService.findVocabularyItemById(vocabularyId, vocabularyItemId);
		}
		if (vi==null) {
			throw new ResourceNotFoundException();
		}
		
		model.addAttribute("vocabularyItem", vi);
		model.addAttribute("isNew", vi.getId().equals("new"));
		
		return "vocabulary/edit_item";
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
