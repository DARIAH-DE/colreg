package eu.dariah.de.colreg.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.dariah.de.colreg.controller.base.BaseController;
import eu.dariah.de.colreg.pojo.TranslationPojo;

@Controller
@RequestMapping("")
public class HomeController extends BaseController {

	@Autowired protected ObjectMapper objectMapper;
	@Autowired protected MessageSource messageSource;
	
	// "" and "/" could also serve for a dashboard
	@RequestMapping(value = {"", "/", "/collections"}, method = RequestMethod.GET)
	public String getCollections(HttpServletResponse response) throws IOException  {
		response.sendRedirect("collections/");
		return null;
	}
	
	// Just for now
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String redirectDashboard(HttpServletResponse response) throws IOException  {
		response.sendRedirect("collections/");
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
