package eu.dariah.de.colreg.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eu.dariah.de.colreg.service.CollectionService;

@Controller
@RequestMapping("/collection")
public class CollectionController {
	@Autowired private CollectionService collectionService;
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String getList(Model model, Locale locale) {
		return "collection/list";
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String getCollection(@PathVariable String id, Model model, Locale locale) {
		return "collection/edit";
	}
}