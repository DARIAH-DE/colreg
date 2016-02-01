package eu.dariah.de.colreg.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.vocabulary.AccessType;
import eu.dariah.de.colreg.model.vocabulary.AccrualMethod;
import eu.dariah.de.colreg.model.vocabulary.AccrualPolicy;
import eu.dariah.de.colreg.service.CollectionService;
import eu.dariah.de.colreg.service.VocabularyService;

@Controller
@RequestMapping("/collections/")
public class CollectionController {
	@Autowired private CollectionService collectionService;
	@Autowired private VocabularyService vocabularyService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String getList(Model model, Locale locale) {		
		model.addAttribute("collections", collectionService.findAllCurrent());
		
		return "collection/list";
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public String editCollection(@PathVariable String id, Model model, Locale locale, HttpServletRequest request) {
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		
		Collection c;
		if (inputFlashMap!=null && inputFlashMap.containsKey("c")) {
			c = (Collection)inputFlashMap.get("c");
		} else if (id.toLowerCase().equals("new")) {
			c = collectionService.createCollection();
		} else {
			c = collectionService.findCurrentByCollectionId(id);
		}
		
		List<AccrualPolicy> policies = vocabularyService.findAllAccrualPolicies();
		List<AccessType> types = vocabularyService.findAllAccessTypes();
		List<AccrualMethod> method = vocabularyService.findAllAccrualMethods();
		
		model.addAttribute("c", c);
		
		return "collection/edit";
	}
	
	// TODO Implement as POST-REDIRECT-GET Pattern
	@RequestMapping(value="{id}", method=RequestMethod.POST)
	public String saveCollection(@Valid Collection c, Model model, Locale locale, final RedirectAttributes redirectAttributes) {
		collectionService.save(c);
		
		// Flash attribute only required on error / otherwise load by id in GET
		redirectAttributes.addFlashAttribute("c", c);
		
		return "redirect:/collections/" + c.getCollectionId();
	}
	
	@RequestMapping(method=GET, value={"/includes/editAccess"})
	public String getEditAccessForm() {
		return "collection/edit/incl/edit_access";
	}
	
	@RequestMapping(method=GET, value={"/includes/editAccrual"})
	public String getEditAccrualForm() {
		return "collection/edit/incl/edit_accrual";
	}
	
	@RequestMapping(method=GET, value={"/includes/editAgent"})
	public String getEditAgentForm() {
		return "collection/edit/incl/edit_agent";
	}
	
	@RequestMapping(method=GET, value={"/includes/editDescription"})
	public String getEditDescriptionForm() {
		return "collection/edit/incl/edit_description";
	}
	
	@RequestMapping(method=GET, value={"/includes/editItemLanguage"})
	public String getEditItemLanguageForm() {
		return "collection/edit/incl/edit_itemlanguage";
	}
}