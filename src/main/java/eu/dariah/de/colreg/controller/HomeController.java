package eu.dariah.de.colreg.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("")
public class HomeController {
	
	// "" and "/" could also serve for a dashboard
	@RequestMapping(value = {"", "/", "/collections"}, method = RequestMethod.GET)
	public String getCollections(HttpServletResponse response) throws IOException  {
		response.sendRedirect("collections/");
		return null;
	}
}
