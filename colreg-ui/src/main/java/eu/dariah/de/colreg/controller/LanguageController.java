package eu.dariah.de.colreg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.dariah.de.colreg.model.vocabulary.Language;
import eu.dariah.de.colreg.service.LanguageService;

@Controller
@RequestMapping("/languages/")
public class LanguageController {
	@Autowired private LanguageService languageService;
	
	@RequestMapping(value="query/{query}", method=RequestMethod.GET)
	public @ResponseBody List<Language> queryLanguages(@PathVariable String query) {
		return languageService.queryLanguages(query);
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody Language getLanguage(@PathVariable String id) {
		Language l = languageService.findLanguageByCode(id);
		if (l==null) {
			l = languageService.findLanguageById(id);
		}
		return l;
	}
}
