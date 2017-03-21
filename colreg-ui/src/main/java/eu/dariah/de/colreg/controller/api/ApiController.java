package eu.dariah.de.colreg.controller.api;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.pojo.CollectionPojo;
import eu.dariah.de.colreg.service.CollectionService;

@Controller
@RequestMapping(value="/api/")
public class ApiController {
	protected static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@Autowired private CollectionService collectionService;
	
	@RequestMapping(value="collections", method=RequestMethod.GET)
	public @ResponseBody List<CollectionPojo> getAllPublic() {
		List<Collection> collections = collectionService.findAllCurrent();
		List<CollectionPojo> collectionPojos = collectionService.convertToPojos(collections, null);
		return collectionPojos;
	}
	
	@RequestMapping(value="collections/{collectionId}", method=RequestMethod.GET)
	public @ResponseBody Collection getCollection(@PathVariable String collectionId) {
		
		return collectionService.findCurrentByCollectionId(collectionId);
		
	}
}
