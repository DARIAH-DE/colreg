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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.model.vocabulary.Language;
import eu.dariah.de.colreg.service.AgentService;
import eu.dariah.de.colreg.service.VocabularyService;

@Controller
@RequestMapping("/agents/")
public class AgentController {
	@Autowired private AgentService agentService;
	@Autowired private VocabularyService vocabularyService;
	
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
			a = agentService.findCurrentByAgentId(id);
		}
		
		model.addAttribute("a", a);
		model.addAttribute("agentTypes", vocabularyService.findAllAgentTypes());
		
		model.addAttribute("parentAgent", a.getParentAgentId()!=null ? agentService.findCurrentByAgentId(a.getParentAgentId()) : null);
		
		return "agent/edit";
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.POST)
	public String saveAgent(@Valid Agent a, Model model, Locale locale, final RedirectAttributes redirectAttributes) {
		agentService.save(a);
		
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