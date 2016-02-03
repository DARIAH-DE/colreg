package eu.dariah.de.colreg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.dariah.de.colreg.model.vocabulary.EncodingScheme;
import eu.dariah.de.colreg.service.VocabularyService;

@Controller
@RequestMapping("/schemes/")
public class EncodingSchemeController {
	@Autowired private VocabularyService vocabularyService;
	
	@RequestMapping(value="query/{query}", method=RequestMethod.GET)
	public @ResponseBody List<EncodingScheme> queryEncodingSchemes(@PathVariable String query) {
		return vocabularyService.queryEncodingSchemes(query);
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody EncodingScheme getEncodingScheme(@PathVariable String id) {
		return vocabularyService.findEncodingSchemeById(id);
	}
}
