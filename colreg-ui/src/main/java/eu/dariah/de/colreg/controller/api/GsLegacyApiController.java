package eu.dariah.de.colreg.controller.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.dariah.federation.model.ColRegCollectionPojo;
import de.dariah.federation.model.ColRegServicePojo;
import eu.dariah.de.colreg.model.Access;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.vocabulary.AccessType;
import eu.dariah.de.colreg.service.AccessTypeService;
import eu.dariah.de.colreg.service.CollectionService;

@Controller
@RequestMapping("/colreg")
public class GsLegacyApiController {
	protected static final Logger logger = LoggerFactory.getLogger(GsLegacyApiController.class);
	
	@Autowired private CollectionService collectionService;
	@Autowired private AccessTypeService accessTypeService;

	@RequestMapping(value={"/collection/listAll", "/collection/listAll/"}, method = RequestMethod.GET, produces="application/json")
	public @ResponseBody List<ColRegCollectionPojo> listAllCollections() {
		List<ColRegCollectionPojo> result = new ArrayList<ColRegCollectionPojo>();
		try {
			List<Collection> collections = collectionService.findAllCurrent();
			if (collections!=null && collections.size()>0) {
				ColRegCollectionPojo cPojo;
				for (Collection c : collections) {
					cPojo = new ColRegCollectionPojo();
					cPojo.setId(c.getEntityId());
					cPojo.setName(c.getLocalizedDescriptions().get(0).getTitle());
					cPojo.setLastModifiedUTC(c.getVersionTimestamp().toInstant().getMillis());
					cPojo.setServices(new ArrayList<ColRegServicePojo>());

					if (c.getAccessMethods()!=null && c.getAccessMethods().size()>0) {
						ColRegServicePojo sPojo;
						for (Access a : c.getAccessMethods()) {
							sPojo = new ColRegServicePojo();
							sPojo.setId(c.getEntityId());
							sPojo.setSchemaUUIDs(a.getSchemeIds());
							sPojo.setServiceURL(a.getUri());
							sPojo.setServiceSubset(a.getOaiSet());
							sPojo.setAccessControl(c.getAccessRights());
							
							AccessType aType = accessTypeService.findAccessTypeById(a.getType());
							if (aType!=null) { 
								if (aType.getIdentifier().equals("oaipmh")) {
									sPojo.setServiceMethod("OAI-PMH");
								} else {
									sPojo.setServiceMethod(aType.getIdentifier());
								}
							}
							cPojo.getServices().add(sPojo);
						}
					}
					result.add(cPojo);
				}
			}
			logger.debug(String.format("Responding to listAll API request...%s collections returned", result.size()));
		} catch (Exception e) {
			logger.error("Failed to respond to listAll service request", e);
		}
		return result;
	}
	
	@RequestMapping(value={"/collectionDetails", "/collectionDetails/", "/serviceDetails", "/serviceDetails/"}, method = RequestMethod.GET)
	public String getCollectionDetails(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect(request.getContextPath() + "/collections/" + id);
			return null;
		} catch (Exception e) {
			return "redirect:/";
		}		 
	}
}
