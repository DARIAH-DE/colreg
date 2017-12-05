package eu.dariah.de.colreg.controller.api;

import java.io.File;
import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.pojo.CollectionPojo;
import eu.dariah.de.colreg.service.CollectionService;
import eu.dariah.de.colreg.service.ImageService;

@Controller
@RequestMapping(value="/api/")
public class ApiController {
	protected static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@Autowired private CollectionService collectionService;
	@Autowired private ImageService imageService;
	
	@RequestMapping(value="collections", method=RequestMethod.GET)
	public @ResponseBody List<CollectionPojo> getAllPublic() {
		List<Collection> collections = collectionService.findAllCurrent();
		List<CollectionPojo> collectionPojos = collectionService.convertToPojos(collections, null);
		return collectionPojos;
	}
	
	@RequestMapping(value="collections/{collectionId}", method=RequestMethod.GET)
	public @ResponseBody Collection getCollection(@PathVariable String collectionId) {
		Collection c = collectionService.findCurrentByCollectionId(collectionId);
		if (c.getCollectionImage()!=null) {
			try {
				File image = imageService.findImage(c.getCollectionImage());
				ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentServletMapping();
				builder.path("/image/" + image.getName());
				URI imageUri = builder.build().toUri();		
				c.setCollectionImage(imageUri.toString());
				
			} catch (Exception e) {
				logger.warn("Failed to load collection image", e);
				c.setCollectionImage(null);
			}
		}
		return c;
	}
}
