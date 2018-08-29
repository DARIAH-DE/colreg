package eu.dariah.de.colreg.controller.api;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.dariah.de.colreg.controller.base.BaseApiController;
import eu.dariah.de.colreg.model.vocabulary.generic.Vocabulary;
import eu.dariah.de.colreg.model.vocabulary.generic.VocabularyItem;
import eu.dariah.de.colreg.pojo.VocabularyItemApiPojo;
import eu.dariah.de.colreg.pojo.api.ApiResultPojo;
import eu.dariah.de.colreg.pojo.api.VocabularyApiResultPojo;
import eu.dariah.de.colreg.pojo.converter.api.VocabularyItemApiConverter;
import eu.dariah.de.colreg.service.VocabularyItemService;
import eu.dariah.de.colreg.service.VocabularyService;

@Controller
@RequestMapping(value="/api/vocabulary")
public class VocabularyApiController extends BaseApiController {
	protected static final Logger logger = LoggerFactory.getLogger(VocabularyApiController.class);
	
	@Autowired private VocabularyService vocabularyService;
	@Autowired private VocabularyItemService vocabularyItemService;
	
	@Autowired private VocabularyItemApiConverter vocabularyItemConverter;
	
	@RequestMapping(value={"/{vocabularyId}", "/{vocabularyId}/"}, method=RequestMethod.GET)
	public @ResponseBody VocabularyApiResultPojo getItems(@PathVariable String vocabularyId,  @RequestParam(required=false) String locale) {
		VocabularyApiResultPojo result = new VocabularyApiResultPojo("listVocabularyItems");
		if (vocabularyId==null || vocabularyId.isEmpty()) {
			result.setMessage("No vocabulary specified");
			result.setSuccess(false);
			return result;
		}
		result = new VocabularyApiResultPojo("listVocabularyItems: " + vocabularyId);
		
		Vocabulary v = vocabularyService.findVocabulary(vocabularyId);
		if (v==null) {
			result.setMessage("No vocabulary found for specified identifier");
			result.setSuccess(false);
			return result;
		}
		
		Locale l = this.handleLocale(result, locale);
		if (!result.isSuccess()) {
			return result;
		}
		
		List<VocabularyItem> vocabularyItems = vocabularyItemService.findVocabularyItems(v.getIdentifier());
		List<VocabularyItemApiPojo> pojos = vocabularyItemConverter.convertToPojos(vocabularyItems, l);
		
		result.setContent(pojos);
		
		return result;
	}
}
