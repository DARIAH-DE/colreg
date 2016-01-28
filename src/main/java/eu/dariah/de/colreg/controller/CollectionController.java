package eu.dariah.de.colreg.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.service.CollectionService;

@Controller
@RequestMapping("/collections/")
public class CollectionController {
	@Autowired private CollectionService collectionService;
	
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String getList(Model model, Locale locale) {
		return "collection/list";
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public String editCollection(@PathVariable String id, Model model, Locale locale) {
		Collection c;
		if (id.toLowerCase().equals("new")) {
			c = collectionService.createCollection();	
		} else {
			c = null; //load collection
		}
		
		model.addAttribute("collection", c);
		
		return "collection/edit";
	}
}