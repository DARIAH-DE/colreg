package eu.dariah.de.colreg.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.dariah.de.colreg.controller.base.VersionedEntityController;
import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.CollectionAgentRelation;
import eu.dariah.de.colreg.model.base.VersionedEntityImpl;
import eu.dariah.de.colreg.pojo.view.EdgePojo;
import eu.dariah.de.colreg.pojo.view.GraphPojo;
import eu.dariah.de.colreg.pojo.view.NodePojo;
import eu.dariah.de.colreg.service.AgentService;
import eu.dariah.de.colreg.service.CollectionService;
import eu.dariah.de.dariahsp.model.web.AuthPojo;

@Controller
@RequestMapping("")
public class HomeController extends VersionedEntityController {

	@Autowired protected ObjectMapper objectMapper;
	@Autowired protected MessageSource messageSource;
	
	@Autowired private CollectionService collectionService;
	@Autowired private AgentService agentService;
	
	@Autowired private ServletContext servletContext;
	
	@Value("#{environment.saml!=null?environment.saml:false}")
	private boolean saml;
	
	public HomeController() {
		super("dashboard");
	}
	
	
	@RequestMapping(value = "/collections", method = RequestMethod.GET)
	public String getCollections(HttpServletResponse response) throws IOException  {
		response.sendRedirect("collections/");
		return null;
	}
	
	// Just for now
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String showHome(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws IOException  {
		List<VersionedEntityImpl> entities = new ArrayList<VersionedEntityImpl>();
		
		AuthPojo auth = authInfoHelper.getAuth(request);
		
		List<Collection> latestCollections = collectionService.findLatestChanges(10, auth);
		if (latestCollections!=null && latestCollections.size()>0) {
			entities.addAll(latestCollections);
		}
		
		List<Agent> latestAgents = agentService.findLatestChanges(10);
		if (latestAgents!=null && latestAgents.size()>0) {
			entities.addAll(latestAgents);
		}
		
		if (entities!=null && entities.size()>0) {
			Collections.sort(entities, new Comparator<VersionedEntityImpl>() {
				@Override
				public int compare(VersionedEntityImpl o1, VersionedEntityImpl o2) {
					if (o1.getVersionTimestamp()==null && o2.getVersionTimestamp()==null) {
						return 0;
					} else if (o1.getVersionTimestamp()==null) {
						return -1;
					} else if (o2.getVersionTimestamp()==null) {
						return 1;
					}
					
					return o1.getVersionTimestamp().compareTo((o2.getVersionTimestamp()));
				}
			});
			Collections.reverse(entities);
		}
		
		this.setUsers(entities);
		model.addAttribute("latest", entities);
		
		model.addAttribute("collectionCount", collectionService.countCollections());
		model.addAttribute("agentCount", agentService.countAgents());
		
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
	public String getLogout(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "url", defaultValue = "/") String url, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException  {	
		if (saml && authInfoHelper.getCurrentUserDetails(request).isAuth()) {
			return "redirect:/saml/logout" + (!url.equals("/") ? "?loginRedirectUrl=" + url : "");
		} else if (!saml && authInfoHelper.getCurrentUserDetails(request).isAuth()) {
			return "redirect:/localsec/logout" + (!url.equals("/") ? "?loginRedirectUrl=" + url : "");
		}
		return "common/logout";
	}
	
	@RequestMapping(value = {"/throw/{code}", "/throw"}, method = RequestMethod.GET)
	public void throwError(@PathVariable(required=false) Integer code, HttpServletRequest request, HttpServletResponse response) throws Exception  {
		throw new Exception("This is an expected error.");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLogin(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "url", defaultValue = "/") String url, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException  {
		if (saml) {
			return "redirect:/saml/login" + (!url.equals("/") ? "?loginRedirectUrl=" + url : "");
		}
		
		if (error != null) {
			model.addAttribute("error", true);
		}
		
		String ctx = servletContext.getContextPath();
		if (url.startsWith(ctx)) {
			url = url.substring(ctx.length());
		}

		model.addAttribute("redirectUrl", url);
		return "common/login";
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
			
			if (agent.getForeName()==null) {
				node.setType("organization");
			} else {
				node.setType("person");
			}
			
			String name = (agent.getForeName()==null? "": (agent.getForeName() + " ")) + agent.getName() ;
			node.setLabel(name);
			nodes.add(node);
			nodeIds.add(node.getId());
		}
		
		// Edges
		for (Collection c : collections) {
			
			// TODO: Migrate to CollectionRelations
			
			/*if (c.getParentCollectionId()!=null && !c.getParentCollectionId().isEmpty()) {
				edge = new EdgePojo();
				edge.setSource(c.getEntityId());
				edge.setTarget(c.getParentCollectionId());
				
				if (!edges.contains(edge) && nodeIds.contains(edge.getSource()) && nodeIds.contains(edge.getTarget())) {
					edges.add(edge);
				}
			}*/
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
}
