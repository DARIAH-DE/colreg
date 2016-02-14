package eu.dariah.de.colreg.controller.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.marshalling.XMLConverter;
import eu.dariah.de.colreg.service.AgentService;
import eu.dariah.de.colreg.service.CollectionService;

@Controller
@RequestMapping("/oaipmh/")
public class OaiPmhController {
	@Autowired private AgentService agentService;
	@Autowired private CollectionService collectionService;
	
	@Autowired private XMLConverter xmlConverter;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public @ResponseBody Object dispatchOaiPmhCommand() throws IOException {
		
		List<Collection> colls = collectionService.findAllCurrent();
		
		xmlConverter.convertFromObjectToXML(colls.get(0), "/tmp/test.xml");
		
		return null;
	}

    @RequestMapping(value = "consume/", method = RequestMethod.POST, 
    		consumes={"application/json", "application/xml"}, produces={"application/json", "application/xml"})
	public @ResponseBody Collection consumeCollection(@RequestBody Collection c, HttpServletResponse response) {
		// validate
    	// validation problem ->  
    	// return collection
		
		// else HttpServletResponse.SC_CREATED or HttpServletResponse.SC_OK 
		
    	
    	response.setStatus(HttpServletResponse.SC_OK);
    	
    	String id = c.getEntityId();
		id = id;
		
		return null;
	}
}
