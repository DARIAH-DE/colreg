package eu.dariah.de.colreg.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eu.dariah.de.colreg.model.Access;
import eu.dariah.de.colreg.model.Accrual;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.CollectionAgentRelation;
import eu.dariah.de.colreg.model.LocalizedDescription;
import eu.dariah.de.colreg.model.validation.CollectionValidator;
import eu.dariah.de.colreg.service.CollectionService;
import eu.dariah.de.colreg.service.VocabularyService;
import eu.dariah.de.minfba.core.web.pojo.ModelActionPojo;

@Controller
@RequestMapping("/collections/")
public class CollectionController {
	@Autowired private CollectionService collectionService;
	@Autowired private VocabularyService vocabularyService;
	
	@Autowired private CollectionValidator validator;
	
	/*@InitBinder
	protected void initBinder(final WebDataBinder binder) {
	    binder.setValidator(validator);
	}*/	
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String getList(Model model, Locale locale) {		
		model.addAttribute("collections", collectionService.findAllCurrent());
		
		return "collection/list";
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public String editCollection(@PathVariable String id, Model model, Locale locale, HttpServletRequest request) {
		Collection c;
		 if (id.toLowerCase().equals("new")) {
			c = collectionService.createCollection();
		} else {
			c = collectionService.findCurrentByCollectionId(id, true);
			if (c!=null) {
				collectionService.initializeAgentRelations(c);
			}
		}
		 
		if (c==null) {
			// Should be 404
			return "redirect:/collections/";
		}
		 
		return fillCollectionEditorModel(id, c, model);
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.POST)
	public String saveCollection(@PathVariable String id, @ModelAttribute Collection collection, BindingResult bindingResult, Model model, Locale locale, final RedirectAttributes redirectAttributes) {
		collection.setEntityId(id);
		validator.validate(collection, bindingResult);
		if (bindingResult.hasErrors()) {
			return this.fillCollectionEditorModel(collection.getEntityId(), collection, model);
		}
		
		// TODO UserId
		collectionService.save(collection, "default user");
		return "redirect:/collections/" + collection.getEntityId();
	}
	
	private String fillCollectionEditorModel(String id, Collection c, Model model) {
		model.addAttribute("collection", c);
		
		model.addAttribute("agentRelationTypes", vocabularyService.findAllAgentRelationTypes());
		model.addAttribute("accessTypes", vocabularyService.findAllAccessTypes());
		model.addAttribute("accrualMethods", vocabularyService.findAllAccrualMethods());
		model.addAttribute("accrualPolicies", vocabularyService.findAllAccrualPolicies());
		model.addAttribute("itemTypes", vocabularyService.findAllItemTypes());
		
		if (c.getParentCollectionId()!=null) {
			model.addAttribute("parentCollection", collectionService.findCurrentByCollectionId(c.getParentCollectionId()));
		}
		
		List<Collection> versions = collectionService.findAllVersionsForEntityId(id);
		model.addAttribute("versions", versions);
		
		List<Collection> childCollections = collectionService.findCurrentByParentCollectionId(id);
		model.addAttribute("childCollections", childCollections);
		model.addAttribute("activeChildCollections", childCollections!=null && childCollections.size()>0);
		model.addAttribute("isDraft", c.getDraftUserId()==null || c.getDraftUserId().equals(""));
		
		return "collection/edit";
	}
	
	@RequestMapping(value="{id}/delete", method=RequestMethod.POST)
	public @ResponseBody ModelActionPojo deleteAgent(@PathVariable String id) {
		ModelActionPojo result = new ModelActionPojo(false);
		List<Collection> children = collectionService.findCurrentByParentCollectionId(id);
				
		if (children==null || children.size()==0) {
			Collection c = collectionService.findCurrentByCollectionId(id);
			c.setDeleted(true);
			// TODO UserId
			collectionService.save(c, "default user");
			result.setSuccess(true);
		}
		
		return result;
	}
	
	@RequestMapping(value="query/{query}", method=RequestMethod.GET)
	public @ResponseBody List<Collection> queryCollections(@PathVariable String query, @RequestParam(required=false) List<String> excl) {
		return collectionService.queryCollections(query, excl);
	}
	
	@RequestMapping(method=GET, value={"/includes/editAccess"})
	public String getEditAccessForm(Model model) {
		Access a = new Access();
		model.addAttribute("currIndex", 0);
		model.addAttribute("currMethod", a);
		model.addAttribute("accessMethods[0]", a);
		model.addAttribute("accessTypes", vocabularyService.findAllAccessTypes());
		return "collection/edit/incl/edit_access";
	}
	
	@RequestMapping(method=GET, value={"/includes/editAccrual"})
	public String getEditAccrualForm(Model model) {
		Accrual a = new Accrual();
		model.addAttribute("currIndex", 0);
		model.addAttribute("currMethod", a);
		model.addAttribute("accrualMethods[0]", a);
		model.addAttribute("accessTypes", vocabularyService.findAllAccessTypes());
		
		model.addAttribute("accrualMethods", vocabularyService.findAllAccrualMethods());
		model.addAttribute("accrualPolicies", vocabularyService.findAllAccrualPolicies());
		return "collection/edit/incl/edit_accrual";
	}
	
	@RequestMapping(method=GET, value={"/includes/editAgent"})
	public String getEditAgentForm(Model model) {
		CollectionAgentRelation ar = new CollectionAgentRelation();
		model.addAttribute("currIndex", 0);
		model.addAttribute("currAgentRelation", ar);
		model.addAttribute("agentRelations[0]", ar);
				
		model.addAttribute("agentRelationTypes", vocabularyService.findAllAgentRelationTypes());
		return "collection/edit/incl/edit_agent";
	}
	
	@RequestMapping(method=GET, value={"/includes/editDescription"})
	public String getEditDescriptionForm(Model model) {
		LocalizedDescription desc = new LocalizedDescription();
		model.addAttribute("currIndex", 0);
		model.addAttribute("currDesc", desc);
		model.addAttribute("localizedDescriptions[0]", desc);
		
		return "collection/edit/incl/edit_description";
	}
	
	@RequestMapping(method=GET, value={"/includes/editItemLanguage"})
	public String getEditItemLanguageForm(Model model) {
		model.addAttribute("currIndex", 0);
		model.addAttribute("currLang", "");
		model.addAttribute("itemLanguages[0]", "");
		
		return "collection/edit/incl/edit_itemlanguage";
	}
	
	@RequestMapping(method=GET, value={"/includes/editSpatial"})
	public String getEditSpatialForm(Model model) {
		model.addAttribute("currIndex", 0);
		model.addAttribute("currSpat", "");
		model.addAttribute("spatials[0]", "");
		
		return "collection/edit/incl/edit_spatial";
	}
	
	@RequestMapping(method=GET, value={"/includes/editSubject"})
	public String getEditSubjectForm(Model model) {
		model.addAttribute("currIndex", 0);
		model.addAttribute("currSubj", "");
		model.addAttribute("subjects[0]", "");
		
		return "collection/edit/incl/edit_subject";
	}
	
	@RequestMapping(method=GET, value={"/includes/editTemporal"})
	public String getEditTemporalForm(Model model) {
		model.addAttribute("currIndex", 0);
		model.addAttribute("currTemp", "");
		model.addAttribute("temporals[0]", "");
		
		return "collection/edit/incl/edit_temporal";
	}
	
	@RequestMapping(method=GET, value={"/includes/editProvidedIdentifier"})
	public String getEditProvidedIdentifierForm(Model model) {
		model.addAttribute("currIndex", 0);
		model.addAttribute("currIdentifier", "");
		model.addAttribute("providedIdentifier[0]", "");
		return "collection/edit/incl/edit_identifier";
	}
	
	@RequestMapping(method=GET, value={"/includes/editEncodingScheme"})
	public String getEditEncodingSchemeForm(Model model) {
		model.addAttribute("currIndex", 0);
		model.addAttribute("subIndex", 0);
		model.addAttribute("currSchemeId", "");
		model.addAttribute("accessMethods[0]", new Access());
		model.addAttribute("accessMethods[0].schemeIds[0]", "");
		
		return "collection/edit/incl/edit_encodingscheme";
	}
	
	@RequestMapping(method=GET, value={"/includes/editAudience"})
	public String getAudienceForm(Model model) {
		model.addAttribute("currIndex", 0);
		model.addAttribute("currAudi", "");
		model.addAttribute("audiences[0]", "");
		
		return "collection/edit/incl/edit_audience";
	}
	
	@RequestMapping(method=GET, value={"/includes/editLocation"})
	public String getLocationForm(Model model) {
		model.addAttribute("currIndex", 0);
		model.addAttribute("currLoc", "");
		model.addAttribute("locations[0]", "");
		
		return "collection/edit/incl/edit_location";
	}
}