package eu.dariah.de.colreg.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import de.unibamberg.minf.core.web.controller.ResourceNotFoundException;
import de.unibamberg.minf.core.web.pojo.ModelActionPojo;
import eu.dariah.de.colreg.controller.base.VersionedEntityController;
import eu.dariah.de.colreg.model.Address;
import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.validation.AgentValidator;
import eu.dariah.de.colreg.model.vocabulary.AgentType;
import eu.dariah.de.colreg.pojo.AgentPojo;
import eu.dariah.de.colreg.pojo.TableListPojo;
import eu.dariah.de.colreg.service.AgentService;
import eu.dariah.de.colreg.service.CollectionService;
import eu.dariah.de.dariahsp.model.web.AuthPojo;

@Controller
@RequestMapping("/agents/")
public class AgentController extends VersionedEntityController {
	@Autowired private AgentService agentService;
	@Autowired private CollectionService collectionService;
	
	@Autowired private AgentValidator validator;
	
	public AgentController() {
		super("agents");
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String getList(Model model, Locale locale) {		
		//model.addAttribute("agents", agentService.findAllCurrent());
		model.addAttribute(NAVIGATION_ELEMENT_ATTRIBUTE, "agents");
		return "agent/list";
	}
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public @ResponseBody TableListPojo<AgentPojo> getAllDrafts(Model model, Locale locale, HttpServletRequest request) {
		List<Agent> agents = agentService.findAllCurrent();
		List<AgentPojo> agentPojos = agentService.convertToPojos(agents, locale);
		
		return new TableListPojo<AgentPojo>(agentPojos);
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public String editAgent(@PathVariable String id, Model model, Locale locale, HttpServletRequest request) throws ResourceNotFoundException {
		AuthPojo auth = authInfoHelper.getAuth(request);
		Agent a;
		if (id.toLowerCase().equals("new")) {
			if (!auth.isAuth()) {
				return "redirect:/" + this.getLoginUrl();
			}
			a = agentService.createAgent();
		} else {
			a = agentService.findCurrentByAgentId(id, true);
			if (a==null) {
				a = agentService.findVersionById(id, true);
			}
		}
		
		if (a==null) {
			throw new ResourceNotFoundException();
		}
		
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap!=null && inputFlashMap.containsKey("lastSavedVersion")) {
			model.addAttribute("lastSavedVersion", inputFlashMap.get("lastSavedVersion"));
			model.addAttribute("lastSavedTimestamp", inputFlashMap.get("lastSavedTimestamp"));
		}
		if (inputFlashMap!=null && inputFlashMap.containsKey("entityWarnings")) {
			model.addAttribute("entityWarnings", inputFlashMap.get("entityWarnings"));
		}
		
		return this.fillAgentEditorModel(a.getEntityId(), a, auth, model);
	}
	
	private String fillAgentEditorModel(String entityId, Agent a, AuthPojo auth, Model model) {
		model.addAttribute("agent", a);
		model.addAttribute("selectedVersionId", a.getId());
		
		List<AgentType> agentTypes = vocabularyService.findAllAgentTypes();
		model.addAttribute("agentTypes", agentTypes);
		
		for (AgentType agentType : agentTypes) {
			if (agentType.getId().equals(a.getAgentTypeId())) {
				model.addAttribute("agentIsNatural", agentType.isNaturalPerson());
				break;
			}
		}
		
		model.addAttribute("parentAgent", a.getParentAgentId()!=null ? agentService.findCurrentByAgentId(a.getParentAgentId()) : null);
		
		List<Agent> children = agentService.findCurrentByParentAgentId(entityId);
		model.addAttribute("childAgents", children);
		model.addAttribute("activeChildAgents", children!=null && children.size()>0);
		
		List<Collection> collections = collectionService.findCurrentByRelatedAgentId(entityId);
		model.addAttribute("collections", collections);
		model.addAttribute("activeCollectionRelation", collections!=null && collections.size()>0);
		
		List<Agent> versions = agentService.findAllVersionsForEntityId(entityId);
		this.setUsers(versions);
		model.addAttribute("versions", versions);
		
		model.addAttribute("isNew", a.getId().equals("new"));
		
		if (a.getSucceedingVersionId()==null) {
			model.addAttribute("isDeleted", a.isDeleted());
		} else {
			Agent current = agentService.findCurrentByAgentId(entityId, true);
			model.addAttribute("isDeleted", current.isDeleted());
		}
		model.addAttribute(NAVIGATION_ELEMENT_ATTRIBUTE, "agents");
		
		if (auth.isAuth() && a.getSucceedingVersionId()==null && !a.isDeleted()) {
			model.addAttribute("editMode", true);
		}
		
		this.setUsers(a);		
		
		return "agent/edit";
	}
	
	@RequestMapping(value="{id}/delete", method=RequestMethod.POST)
	public @ResponseBody ModelActionPojo deleteAgent(@PathVariable String id, HttpServletRequest request) {
		AuthPojo auth = authInfoHelper.getAuth(request);
		ModelActionPojo result = new ModelActionPojo(false);
		if (!auth.isAuth()) {
			return result;
		}
		
		List<Agent> children = agentService.findCurrentByParentAgentId(id);
		List<Collection> collections = collectionService.findCurrentByRelatedAgentId(id);
		
		if ((children==null || children.size()==0) && (collections==null || collections.size()==0)) {
			Agent a = agentService.findCurrentByAgentId(id);
			a.setDeleted(true);
			agentService.save(a, auth.getUserId());
			result.setSuccess(true);
		}
		
		return result;
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.POST)
	public String saveAgent(@PathVariable String id, @ModelAttribute Agent agent, BindingResult bindingResult, Model model, Locale locale, final RedirectAttributes redirectAttributes, HttpServletRequest request) {
		
		AuthPojo auth = authInfoHelper.getAuth(request);
		if (!auth.isAuth()) {
			return "redirect:/" + this.getLoginUrl();
		}
		agent.setEntityId(id);
				
		validator.validate(agent, bindingResult);
		
		List<String> entityWarnings = validator.getEntityWarnings(agent);
		if (bindingResult.hasErrors()) {
			model.addAttribute("entityWarnings", entityWarnings);
			return this.fillAgentEditorModel(id, agent, authInfoHelper.getAuth(request), model);
		} 
		agentService.save(agent, auth.getUserId());
		redirectAttributes.addFlashAttribute("lastSavedVersion", agent.getId());
		redirectAttributes.addFlashAttribute("entityWarnings", entityWarnings);
		redirectAttributes.addFlashAttribute("lastSavedTimestamp", agent.getVersionTimestamp());
		return "redirect:/agents/" + agent.getEntityId();
	}
	
	@RequestMapping(value="query/{query}", method=RequestMethod.GET)
	public @ResponseBody List<Agent> queryAgents(@PathVariable String query, @RequestParam(required=false) List<String> excl) {
		return agentService.queryAgents(query, excl);
	}
	
	@RequestMapping(method=RequestMethod.POST, value={"{id}/commentVersion/{versionid}"})
	public @ResponseBody ModelActionPojo appendVersionComment(@PathVariable String id, @PathVariable String versionid, @RequestParam String comment) {
		agentService.appendVersionComment(versionid, comment);

		return new ModelActionPojo(true);
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
		model.addAttribute("editMode", true);
		return "agent/edit/incl/edit_address";
	}
}