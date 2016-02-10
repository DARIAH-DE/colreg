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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import eu.dariah.de.colreg.model.Address;
import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.validation.AgentValidator;
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
		
	@RequestMapping(value="", method=RequestMethod.GET)
	public String getList(Model model, Locale locale) {		
		model.addAttribute("agents", agentService.findAllCurrent());
		
		return "agent/list";
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public String editAgent(@PathVariable String id, Model model, Locale locale, HttpServletRequest request) {
		Agent a;
		if (id.toLowerCase().equals("new")) {
			a = agentService.createAgent();
		} else {
			a = agentService.findCurrentByAgentId(id, true);
			if (a==null) {
				a = agentService.findVersionById(id, true);
			}
		}
		
		if (a==null) {
			// Should be 404
			return "redirect:/collections/";
		}
		
		return this.fillAgentEditorModel(a.getEntityId(), a, model);
	}
	
	private String fillAgentEditorModel(String entityId, Agent a, Model model) {
		model.addAttribute("agent", a);
		model.addAttribute("selectedVersionId", a.getId());
		
		model.addAttribute("agentTypes", vocabularyService.findAllAgentTypes());
		model.addAttribute("parentAgent", a.getParentAgentId()!=null ? agentService.findCurrentByAgentId(a.getParentAgentId()) : null);
		
		List<Agent> children = agentService.findCurrentByParentAgentId(entityId);
		model.addAttribute("childAgents", children);
		model.addAttribute("activeChildAgents", children!=null && children.size()>0);
		
		List<Collection> collections = collectionService.findCurrentByRelatedAgentId(entityId);
		model.addAttribute("collections", collections);
		model.addAttribute("activeCollectionRelation", collections!=null && collections.size()>0);
		
		List<Agent> versions = agentService.findAllVersionsForEntityId(entityId);
		model.addAttribute("versions", versions);
		
		model.addAttribute("isNew", a.getId().equals("new"));
		
		if (a.getSucceedingVersionId()==null) {
			model.addAttribute("isDeleted", a.isDeleted());
		} else {
			Agent current = agentService.findCurrentByAgentId(entityId, true);
			model.addAttribute("isDeleted", current.isDeleted());
		}
		
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
			agentService.save(a, "default_user");
			result.setSuccess(true);
		}
		
		return result;
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.POST)
	public String saveAgent(@PathVariable String id, @ModelAttribute Agent agent, BindingResult bindingResult, Model model, Locale locale, final RedirectAttributes redirectAttributes) {
		agent.setEntityId(id);
		validator.validate(agent, bindingResult);
		if (bindingResult.hasErrors()) {
			return this.fillAgentEditorModel(id, agent, model);
		} 
		agentService.save(agent, "default_user");
		return "redirect:/agents/" + agent.getEntityId();
	}
	
	@RequestMapping(value="query/{query}", method=RequestMethod.GET)
	public @ResponseBody List<Agent> queryAgents(@PathVariable String query, @RequestParam(required=false) List<String> excl) {
		return agentService.queryAgents(query, excl);
	}
	
	@RequestMapping(method=GET, value={"/includes/editIdentifier"})
	public String getEditIdentifierForm() {
		return "agent/edit/incl/edit_identifier";
	}
	
	@RequestMapping(method=GET, value={"/includes/editAddress"})
	public String getEditAddressForm(Model model) {
		Address a = new Address();
		model.addAttribute("currIndex", 0);
		model.addAttribute("currAddr", a);
		model.addAttribute("addresses[0]", a);
		return "agent/edit/incl/edit_address";
	}
}