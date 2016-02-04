package eu.dariah.de.colreg.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.validation.AgentValidator;
import eu.dariah.de.colreg.model.vocabulary.Language;
import eu.dariah.de.colreg.service.AgentService;
import eu.dariah.de.colreg.service.CollectionService;
import eu.dariah.de.colreg.service.VocabularyService;
import eu.dariah.de.minfba.core.web.pojo.ModelActionPojo;

@Controller
@RequestMapping("/agents/")
public class AgentController {
	@Autowired private AgentService agentService;
	@Autowired private CollectionService collectionService;
	@Autowired private VocabularyService vocabularyService;
	
	@Autowired private AgentValidator validator;
	
	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
	    binder.addValidators(validator);
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String getList(Model model, Locale locale) {		
		model.addAttribute("agents", agentService.findAllCurrent());
		
		return "agent/list";
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public String editAgent(@PathVariable String id, Model model, Locale locale, HttpServletRequest request) {
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		
		Agent a;
		if (inputFlashMap!=null && inputFlashMap.containsKey("a")) {
			a = (Agent)inputFlashMap.get("a");
		} else if (id.toLowerCase().equals("new")) {
			a = agentService.createAgent();
		} else {
			a = agentService.findCurrentByAgentId(id, true);
		}
		
		model.addAttribute("a", a);
		model.addAttribute("agentTypes", vocabularyService.findAllAgentTypes());
		model.addAttribute("parentAgent", a.getParentAgentId()!=null ? agentService.findCurrentByAgentId(a.getParentAgentId()) : null);
		
		List<Agent> children = agentService.findCurrentByParentAgentId(id);
		model.addAttribute("childAgents", children);
		model.addAttribute("activeChildAgents", children!=null && children.size()>0);
		
		List<Collection> collections = collectionService.findCurrentByRelatedAgentId(id);
		model.addAttribute("collections", collections);
		model.addAttribute("activeCollectionRelation", collections!=null && collections.size()>0);
						
		return "agent/edit";
	}
	
	@RequestMapping(value="{id}/delete", method=RequestMethod.POST)
	public @ResponseBody ModelActionPojo deleteAgent(@PathVariable String id) {
		ModelActionPojo result = new ModelActionPojo(false);
		List<Agent> children = agentService.findCurrentByParentAgentId(id);
		List<Collection> collections = collectionService.findCurrentByRelatedAgentId(id);
		
		if ((children==null || children.size()==0) && (collections==null || collections.size()==0)) {
			Agent a = agentService.findCurrentByAgentId(id);
			a.setDeleted(true);
			agentService.save(a);
			result.setSuccess(true);
		}
		
		return result;
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.POST)
	public String saveAgent(@Valid Agent a, BindingResult bindingResult, Model model, Locale locale, final RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			
		} else {
			agentService.save(a);
		}
		
		// Flash attribute only required on error / otherwise load by id in GET
		redirectAttributes.addFlashAttribute("a", a);
						
		return "redirect:/agents/" + a.getEntityId();
	}
	
	@RequestMapping(value="query/{query}", method=RequestMethod.GET)
	public @ResponseBody List<Agent> queryAgents(@PathVariable String query, @RequestParam(required=false) List<String> excl) {
		return agentService.queryAgents(query, excl);
	}
	
	@RequestMapping(method=GET, value={"/includes/editIdentifier"})
	public String getEditAccessForm() {
		return "agent/edit/incl/edit_identifier";
	}
}