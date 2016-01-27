package eu.dariah.de.colreg.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/collection")
public class CollectionController {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String getList(Model model, Locale locale) {
		return "collection/list";
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String getCollection(@PathVariable String id, Model model, Locale locale) {
		return "collection/edit";
	}
}