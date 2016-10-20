package eu.dariah.de.colreg.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.dariah.de.colreg.controller.base.BaseController;
import eu.dariah.de.colreg.controller.base.VersionedEntityController;
import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.CollectionAgentRelation;
import eu.dariah.de.colreg.model.PersistedUserDetails;
import eu.dariah.de.colreg.model.base.VersionedEntityImpl;
import eu.dariah.de.colreg.model.vocabulary.Language;
import eu.dariah.de.colreg.pojo.CollectionPojo;
import eu.dariah.de.colreg.pojo.EdgePojo;
import eu.dariah.de.colreg.pojo.GraphPojo;
import eu.dariah.de.colreg.pojo.NodePojo;
import eu.dariah.de.colreg.pojo.TranslationPojo;
import eu.dariah.de.colreg.service.AgentService;
import eu.dariah.de.colreg.service.CollectionService;

@Controller
@RequestMapping("")
public class HomeController extends VersionedEntityController {

	@Autowired protected ObjectMapper objectMapper;
	@Autowired protected MessageSource messageSource;
	
	@Autowired private CollectionService collectionService;
	@Autowired private AgentService agentService;
	
	@RequestMapping(value = "/collections", method = RequestMethod.GET)
	public String getCollections(HttpServletResponse response) throws IOException  {
		response.sendRedirect("collections/");
		return null;
	}
	
	
	
	// Just for now
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String showHome(Model model, HttpServletResponse response, Locale locale) throws IOException  {
		List<VersionedEntityImpl> entities = new ArrayList<VersionedEntityImpl>();
		
		List<Collection> latestCollections = collectionService.findLatestChanges(5);
		if (latestCollections!=null && latestCollections.size()>0) {
			entities.addAll(latestCollections);
		}
		
		List<Agent> latestAgents = agentService.findLatestChanges(5);
		if (latestAgents!=null && latestAgents.size()>0) {
			entities.addAll(latestAgents);
		}
		
		Collections.sort(entities, new Comparator<VersionedEntityImpl>() {
			@Override
			public int compare(VersionedEntityImpl o1, VersionedEntityImpl o2) {
				return o1.getVersionTimestamp().compareTo((o2.getVersionTimestamp()));
			}
		});
		Collections.reverse(entities);
		
		this.setUsers(entities);
		model.addAttribute("latest", entities);
		return "home";
	}
	
	// Just for now
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String redirectDashboard(HttpServletResponse response) throws IOException  {
		response.sendRedirect("/");
		return null;
	}
	
	@RequestMapping(value = {"/colreg/main", "/colreg/main/"}, method = RequestMethod.GET)
	public String redirectMessedUpEntry(HttpServletResponse response, HttpServletRequest request) throws IOException  {
		response.sendRedirect(request.getContextPath() + "/home"); 
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		return null;
	}
	
	@RequestMapping(value = "/agents", method = RequestMethod.GET)
	public String redirectAgents(HttpServletResponse response) throws IOException  {
		response.sendRedirect("agents/");
		return null;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String showLogout(HttpServletResponse response) throws IOException  {
		return "logout";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLogin(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "url", defaultValue = "/") String url, HttpServletResponse response, Model model) throws IOException  {
		if (error != null) {
			model.addAttribute("error", true);
		}
		model.addAttribute("redirectUrl", url);
		return "login";
	}
	
	@RequestMapping(value="/async/graph", method=RequestMethod.GET)
	public @ResponseBody GraphPojo getGraph() {
		// TODO: Just for now...should query th id only
		List<Collection> collections = collectionService.findAllCurrent();
		List<Agent> agents = agentService.findAllCurrent();
		List<NodePojo> nodes = new ArrayList<NodePojo>();
		List<EdgePojo> edges = new ArrayList<EdgePojo>();
		List<String> nodeIds = new ArrayList<String>();
		
		NodePojo node;
		EdgePojo edge;
		
		// Nodes
		for (Collection c : collections) {
			node = new NodePojo();
			node.setId(c.getEntityId());
			node.setLabel(c.getLocalizedDescriptions().get(0).getTitle());
			node.setType("collection");
			nodes.add(node);
			nodeIds.add(node.getId());
		}
		for (Agent agent : agents) {
			node = new NodePojo();
			node.setId(agent.getEntityId());
			node.setType("agent");
			
			String name = (agent.getForeName()==null? "": (agent.getForeName() + " ")) + agent.getName() ;
			node.setLabel(name);
			nodes.add(node);
			nodeIds.add(node.getId());
		}
		
		// Edges
		for (Collection c : collections) {
			if (c.getParentCollectionId()!=null && !c.getParentCollectionId().isEmpty()) {
				edge = new EdgePojo();
				edge.setSource(c.getEntityId());
				edge.setTarget(c.getParentCollectionId());
				
				if (!edges.contains(edge) && nodeIds.contains(edge.getSource()) && nodeIds.contains(edge.getTarget())) {
					edges.add(edge);
				}
			}
			if (c.getAgentRelations()!=null && c.getAgentRelations().size()!=0) {
				for (CollectionAgentRelation car : c.getAgentRelations()) {
					edge = new EdgePojo();
					edge.setSource(c.getEntityId());
					edge.setTarget(car.getAgentId());
					if (!edges.contains(edge) && nodeIds.contains(edge.getSource()) && nodeIds.contains(edge.getTarget())) {
						edges.add(edge);
					}
				}
			}
		}
		for (Agent agent : agents) {
			if (agent.getParentAgentId()!=null && !agent.getParentAgentId().isEmpty()) {
				edge = new EdgePojo();
				edge.setSource(agent.getEntityId());
				edge.setTarget(agent.getParentAgentId());
				
				if (!edges.contains(edge) && nodeIds.contains(edge.getSource()) && nodeIds.contains(edge.getTarget())) {
					edges.add(edge);
				}
			}
		}
		
		GraphPojo graph = new GraphPojo();
		graph.setNodes(nodes);
		graph.setEdges(edges);
		
		return graph;		
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/translate", produces = "application/json; charset=utf-8")
	public @ResponseBody List<TranslationPojo> getTranslations(Model model, @RequestParam String keys, Locale locale) {	
		ObjectMapper m = new ObjectMapper();
		List<TranslationPojo> translations = null;
		
		try {
			translations = m.readValue(keys, m.getTypeFactory().constructCollectionType(List.class, TranslationPojo.class));
			for (TranslationPojo t : translations) {
				t.setTranslation(messageSource.getMessage(t.getKey(), t.getArgs(), locale));
			}
			
		} catch (IOException e) {
			logger.error("Message error", e);
		}
											
		return translations;
	}
}
