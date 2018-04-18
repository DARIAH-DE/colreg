package eu.dariah.de.colreg.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.dariah.de.colreg.controller.base.BaseApiController;
import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.pojo.api.ApiResultPojo;
import eu.dariah.de.colreg.pojo.api.CollectionApiPojo;
import eu.dariah.de.colreg.pojo.api.ExtendedCollectionApiPojo;
import eu.dariah.de.colreg.pojo.converter.api.CollectionApiConverter;
import eu.dariah.de.colreg.pojo.converter.api.ExtendedCollectionApiConverter;
import eu.dariah.de.colreg.service.AccessTypeService;
import eu.dariah.de.colreg.service.AccrualMethodService;
import eu.dariah.de.colreg.service.AccrualPeriodicityService;
import eu.dariah.de.colreg.service.AccrualPolicyService;
import eu.dariah.de.colreg.service.AgentService;
import eu.dariah.de.colreg.service.AgentTypeService;
import eu.dariah.de.colreg.service.CollectionService;

@Controller
@RequestMapping(value="/api/collections")
public class CollectionApiController extends BaseApiController {	
	@Autowired private CollectionService collectionService;
	@Autowired private CollectionApiConverter collectionApiConverter;
	@Autowired private ExtendedCollectionApiConverter extendedCollectionApiConverter;
	
	@Autowired private AgentTypeService agentTypeService;
	@Autowired private AccessTypeService accessTypeService;
	@Autowired private AccrualMethodService accrualMethodService;
	@Autowired private AccrualPolicyService accrualPolicyService;
	@Autowired private AccrualPeriodicityService accrualPeriodicityService;
	@Autowired private AgentService agentService;
	
	@RequestMapping(value={"", "/"}, method=RequestMethod.GET)
	public @ResponseBody ApiResultPojo<List<CollectionApiPojo>> getAllPublic(@RequestParam(required=false) String locale) {
		ApiResultPojo<List<CollectionApiPojo>> result = new ApiResultPojo<List<CollectionApiPojo>>("listCollections");
		Locale l = this.handleLocale(result, locale);
		if (!result.isSuccess()) {
			return result;
		}
			
		Map<String, String> agentTypeIdLabelMap = agentTypeService.findAgentTypeIdLabelMap();
		Map<String, Agent> agentIdMap = this.getAgentIdMap();
		
		List<Collection> collections = collectionService.findAllCurrent();
		List<CollectionApiPojo> collectionPojos = collectionApiConverter.convertToPojos(collections, l, agentIdMap, agentTypeIdLabelMap);
		
		result.setContent(collectionPojos);
		
		return result;
	}
	
	@RequestMapping(value="/{collectionId}", method=RequestMethod.GET)
	public @ResponseBody ApiResultPojo<ExtendedCollectionApiPojo> getCollection(@PathVariable String collectionId, @RequestParam(required=false) String locale) {
		ApiResultPojo<ExtendedCollectionApiPojo> result = new ApiResultPojo<ExtendedCollectionApiPojo>("getCollection");
		Locale l = this.handleLocale(result, locale);
		if (!result.isSuccess()) {
			return result;
		}
		
		Collection c = collectionService.findCurrentByCollectionId(collectionId);
		if (c==null) {
			result.setMessage("No collection matches specified ID.");
			result.setSuccess(false);
			return result;
		}
		
		Map<String, String> agentTypeIdLabelMap = agentTypeService.findAgentTypeIdLabelMap();
		Map<String, String> accessTypeIdLabelMap = accessTypeService.findAccessTypeIdLabelsMap(); 
		Map<String, String> accrualMethodIdIdentifierMap = accrualMethodService.findAccrualMethodIdIdentifierMap(); 
		Map<String, String> accrualPolicyIdIdentifierMap = accrualPolicyService.findAccrualPolicyIdIdentifierMap(); 
		Map<String, String> accrualPeriodicityIdIdentifierMap = accrualPeriodicityService.findAccrualPeriodicityIdIdentifierMap();
		
		Map<String, Agent> agentIdMap = this.getAgentIdMap();

		result.setContent(extendedCollectionApiConverter.convertToPojo(c, l, agentIdMap, agentTypeIdLabelMap, accessTypeIdLabelMap, accrualMethodIdIdentifierMap, accrualPolicyIdIdentifierMap, accrualPeriodicityIdIdentifierMap));
		return result;
	}
	
	private Map<String, Agent> getAgentIdMap() {
		Map<String, Agent> agentIdMap = new HashMap<String, Agent>();
		for (Agent a : agentService.findAllCurrent()) {
			agentIdMap.put(a.getEntityId(), a);
		}
		return agentIdMap;
	} 
}
