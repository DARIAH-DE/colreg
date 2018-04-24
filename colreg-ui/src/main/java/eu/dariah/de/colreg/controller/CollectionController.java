package eu.dariah.de.colreg.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import de.unibamberg.minf.core.web.controller.ResourceNotFoundException;
import de.unibamberg.minf.core.web.pojo.ModelActionPojo;
import de.unibamberg.minf.core.web.service.ImageService;
import de.unibamberg.minf.core.web.service.ImageServiceImpl.ImageTypes;
import eu.dariah.de.colreg.controller.base.VersionedEntityController;
import eu.dariah.de.colreg.model.Access;
import eu.dariah.de.colreg.model.Accrual;
import eu.dariah.de.colreg.model.Address;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.CollectionAgentRelation;
import eu.dariah.de.colreg.model.CollectionRelation;
import eu.dariah.de.colreg.model.LocalizedDescription;
import eu.dariah.de.colreg.model.validation.CollectionValidator;
import eu.dariah.de.colreg.model.vocabulary.generic.VocabularyItem;
import eu.dariah.de.colreg.pojo.ImagePojo;
import eu.dariah.de.colreg.pojo.converter.ImageConverter;
import eu.dariah.de.colreg.pojo.converter.view.CollectionRelationViewConverter;
import eu.dariah.de.colreg.pojo.converter.view.CollectionViewConverter;
import eu.dariah.de.colreg.pojo.converter.view.VocabularyItemViewConverter;
import eu.dariah.de.colreg.pojo.view.CollectionRelationViewPojo;
import eu.dariah.de.colreg.pojo.view.CollectionViewPojo;
import eu.dariah.de.colreg.pojo.view.TableListPojo;
import eu.dariah.de.colreg.pojo.view.VocabularyItemViewPojo;
import eu.dariah.de.colreg.service.AccessTypeService;
import eu.dariah.de.colreg.service.AccrualMethodService;
import eu.dariah.de.colreg.service.AccrualPeriodicityService;
import eu.dariah.de.colreg.service.AccrualPolicyService;
import eu.dariah.de.colreg.service.AgentRelationTypeService;
import eu.dariah.de.colreg.service.CollectionService;
import eu.dariah.de.colreg.service.LicenseService;
import eu.dariah.de.colreg.service.SchemaService;
import eu.dariah.de.colreg.service.UnitOfMeasurementService;
import eu.dariah.de.colreg.service.VocabularyItemService;
import eu.dariah.de.dariahsp.model.web.AuthPojo;

@Controller
@RequestMapping(value={"/collections/", "/drafts/"})
public class CollectionController extends VersionedEntityController {
	@Autowired private CollectionService collectionService;
	@Autowired private CollectionViewConverter collectionPojoConverter;
	
	@Autowired private SchemaService schemaService;
	
	@Autowired private CollectionValidator validator;
	@Autowired private LicenseService licenseService;
	
	@Autowired private CollectionRelationViewConverter collectionRelationConverter;
	
	@Autowired private ImageService imageService;
	@Autowired private ImageConverter imageConverter;
	
	@Autowired protected VocabularyItemService vocabularyItemService;
	@Autowired protected VocabularyItemViewConverter vocabularyItemConverter;
	
	@Autowired private AgentRelationTypeService agentRelationTypeService;
	@Autowired private AccessTypeService accessTypeService;
	@Autowired private AccrualMethodService accrualMethodService;
	@Autowired private AccrualPolicyService accrualPolicyService;
	@Autowired private AccrualPeriodicityService accrualPeriodicityService;
	@Autowired private UnitOfMeasurementService unitOfMeasurementService;
		
	
	@ModelAttribute("_collectionTypesVocabularyId")
	public String getCollectionTypesVocabularyId() {
		return vocabularyService.findVocabularyByIdentifier(Collection.COLLECTION_TYPES_VOCABULARY_IDENTIFIER).getId();
	}
	
	@ModelAttribute("_itemTypesVocabularyId")
	public String getItemTypesVocabularyId() {
		return vocabularyService.findVocabularyByIdentifier(Collection.ITEM_TYPES_VOCABULARY_IDENTIFIER).getId();
	}
	
	@ModelAttribute("_collectionRelationTypesVocabularyId")
	public String getCollectionRelationTypesVocabularyId() {
		return vocabularyService.findVocabularyByIdentifier(CollectionRelation.COLLECTION_RELATION_TYPES_VOCABULARY_IDENTIFIER).getId();
	}
	
	public CollectionController() {
		super("collections");
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String getList(Model model, Locale locale, HttpServletRequest request) {		
		model.addAttribute("collections", collectionService.findAllCurrent());
		if(request.getServletPath().equals("/collections/")) {
			model.addAttribute("load", "collections");
			model.addAttribute(NAVIGATION_ELEMENT_ATTRIBUTE, "collections");
		} else {
			model.addAttribute("load", "drafts");
			model.addAttribute(NAVIGATION_ELEMENT_ATTRIBUTE, "drafts");
		}
		
		return "collection/list";
	}
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public @ResponseBody TableListPojo<CollectionViewPojo> getAllPublic(Model model, Locale locale, HttpServletRequest request) {
		List<Collection> collections = null;
		if(request.getServletPath().equals("/drafts/list")) {
			AuthPojo auth = authInfoHelper.getAuth(request);
			if (auth.isAuth()) {
				collections = collectionService.findAllDrafts(auth.getUserId());
			}
		} else {
			collections = collectionService.findAllCurrent();
		}
		Map<String, String> accessTypeIdLabelsMap = accessTypeService.findAccessTypeIdLabelsMap();
		List<CollectionViewPojo> collectionPojos = collectionPojoConverter.convertToPojos(collections, locale, accessTypeIdLabelsMap);
		return new TableListPojo<CollectionViewPojo>(collectionPojos);
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public String editCollection(@PathVariable String id, Model model, Locale locale, HttpServletRequest request) {
		AuthPojo auth = authInfoHelper.getAuth(request);
		Collection c;
		if (id.toLowerCase().equals("new")) {
			if (!auth.isAuth()) {
				return "redirect:/" + this.getLoginUrl();
			}
			c = collectionService.createCollection(auth.getUserId());
		} else {
			c = collectionService.findCurrentByCollectionId(id, true);
			if (c==null) {
				c = collectionService.findVersionById(id, true);
			}
			if (c!=null) {
				collectionService.initializeAgentRelations(c);
			}
		}
		
		model.addAttribute("locale", locale.getLanguage());
		 
		if (c==null) {
			throw new ResourceNotFoundException();
		}
		
		if (c.getDraftUserId()!=null && !c.getDraftUserId().trim().isEmpty() &&
				!c.getDraftUserId().equals(auth.getUserId())) {
			// Should be 403
			return "redirect:/collections/";
		}
		
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap!=null && inputFlashMap.containsKey("lastSavedVersion")) {
			model.addAttribute("lastSavedVersion", inputFlashMap.get("lastSavedVersion"));
			model.addAttribute("lastSavedTimestamp", inputFlashMap.get("lastSavedTimestamp"));
		}
		
		return fillCollectionEditorModel(c.getEntityId(), c, auth, model, locale);
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.POST)
	public String saveCollection(@PathVariable String id, @ModelAttribute Collection collection, BindingResult bindingResult, Model model, Locale locale, 
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {
		AuthPojo auth = authInfoHelper.getAuth(request);
		if (!auth.isAuth()) {
			return "redirect:/" + this.getLoginUrl();
		}
		
		return this.doSave(collection, id, false, bindingResult, auth, model, redirectAttributes, locale);
	}
	
	@RequestMapping(value="{id}/publish", method=RequestMethod.POST)
	public String publicCollection(@PathVariable String id, @ModelAttribute Collection collection, BindingResult bindingResult, Model model, Locale locale, 
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {
		AuthPojo auth = authInfoHelper.getAuth(request);
		if (!auth.isAuth()) {
			return "redirect:/" + this.getLoginUrl();
		}
		
		return this.doSave(collection, id, true, bindingResult, auth, model, redirectAttributes, locale);
	}	
	
	private String doSave(Collection collection, String entityId, boolean doPublish, BindingResult bindingResult, AuthPojo auth, Model model, final RedirectAttributes redirectAttributes, Locale locale) {
		collection.setEntityId(entityId);
		validator.validate(collection, bindingResult);
				
		if (bindingResult.hasErrors()) {
			return this.fillCollectionEditorModel(collection.getEntityId(), collection, auth, model, locale);
		}
		
		if (doPublish) {
			collection.setDraftUserId(null);
		} else {
			Collection cCurrent = collectionService.findCurrentByCollectionId(collection.getEntityId());
			if (cCurrent==null) {
				collection.setDraftUserId(auth.getUserId());
			} else {
				// Stays with its draft creator or published
				collection.setDraftUserId(cCurrent.getDraftUserId());
			}
		}
		//collectionService.save(collection, auth.getUserId());
		redirectAttributes.addFlashAttribute("lastSavedVersion", collection.getId());
		redirectAttributes.addFlashAttribute("lastSavedTimestamp", collection.getVersionTimestamp());
		return "redirect:/collections/" + collection.getEntityId();
	}
	
	@RequestMapping(method=RequestMethod.POST, value={"{id}/commentVersion/{versionid}"})
	public @ResponseBody ModelActionPojo appendVersionComment(@PathVariable String id, @PathVariable String versionid, @RequestParam String comment) {
		collectionService.appendVersionComment(versionid, comment);

		return new ModelActionPojo(true);
	}
	
	private String fillCollectionEditorModel(String entityId, Collection c, AuthPojo auth, Model model, Locale locale) {
		model.addAttribute("collection", c);
		
		if (c.getAccessRights()==null || ObjectId.isValid(c.getAccessRights())) {
			model.addAttribute("accessRightsIsLicenseId", true);
		} else {
			model.addAttribute("accessRightsIsLicenseId", false);
		}
		
		if (c.getCollectionImageRights()==null || ObjectId.isValid(c.getCollectionImageRights())) {
			model.addAttribute("collectionImageRightsIsLicenseId", true);
		} else {
			model.addAttribute("collectionImageRightsIsLicenseId", false);
		}
		
		if (c.getCollectionDescriptionRights()==null || ObjectId.isValid(c.getCollectionDescriptionRights())) {
			model.addAttribute("collectionDescriptionRightsIsLicenseId", true);
		} else {
			model.addAttribute("collectionDescriptionRightsIsLicenseId", false);
		}

		if (c.getItemRights()==null || ObjectId.isValid(c.getItemRights())) {
			model.addAttribute("itemRightsIsLicenseId", true);
		} else {
			model.addAttribute("itemRightsIsLicenseId", false);
		}
		
		model.addAttribute("licenseGroups", licenseService.findAllLicenseGroups());
		
		model.addAttribute("selectedVersionId", c.getId());
		
		model.addAttribute("agentRelationTypes", agentRelationTypeService.findAllAgentRelationTypes());
		model.addAttribute("accessTypes", accessTypeService.findAllAccessTypes());
		model.addAttribute("accrualMethods", accrualMethodService.findAllAccrualMethods());
		model.addAttribute("accrualPolicies", accrualPolicyService.findAllAccrualPolicies());
		model.addAttribute("accrualPeriodicities", accrualPeriodicityService.findAllAccrualPeriodicities());
		model.addAttribute("encodingSchemes", schemaService.findAllSchemas());
		model.addAttribute("unitsOfMeasurement", unitOfMeasurementService.findAllUnitsOfMeasurement());
	
		model.addAttribute("collectionImages", imageConverter.convertToPojos(c.getCollectionImages()));

		if (c.getRelations()!=null) {
			List<String> relatedCollectionIds = new ArrayList<String>();
			for (CollectionRelation cr : c.getRelations()) {
				if (!relatedCollectionIds.contains(cr.getSourceEntityId())) {
					relatedCollectionIds.add(cr.getSourceEntityId());
				}
				if (!relatedCollectionIds.contains(cr.getTargetEntityId())) {
					relatedCollectionIds.add(cr.getTargetEntityId());
				}
			}
			List<Collection> relatedCollections = collectionService.findCurrentByCollectionIds(relatedCollectionIds);
			Map<String, CollectionViewPojo> relatedCollectionEntityIdPojoMap = new HashMap<String, CollectionViewPojo>(relatedCollections.size()); 
			for (CollectionViewPojo pojo : collectionPojoConverter.convertToPojos(relatedCollections, locale)) {
				relatedCollectionEntityIdPojoMap.put(pojo.getId(), pojo);
			}
			
			model.addAttribute("relations", collectionRelationConverter.convertToPojos(c.getRelations(), locale, relatedCollectionEntityIdPojoMap));
		}
		
		List<Collection> versions = collectionService.findAllVersionsForEntityId(entityId);
		this.setUsers(versions);
		
		model.addAttribute("versions", versions);
		
		List<Collection> childCollections = collectionService.findCurrentByParentCollectionId(entityId);
		model.addAttribute("childCollections", childCollections);
		model.addAttribute("activeChildCollections", childCollections!=null && childCollections.size()>0);
		model.addAttribute("isDraft", (c.getDraftUserId()!=null && !c.getDraftUserId().equals("")) || c.getId().equals("new"));
		model.addAttribute("isNew", c.getId().equals("new"));
		if (auth!=null) {		
			model.addAttribute("currentUserId", auth.getUserId());
		} else {
			model.addAttribute("currentUserId", "-1");
		}
		
		if (c.getSucceedingVersionId()==null) {
			model.addAttribute("isDeleted", c.isDeleted());
		} else {
			Collection current = collectionService.findCurrentByCollectionId(entityId, true);
			model.addAttribute("isDeleted", current.isDeleted());
		}
		if (c.getDraftUserId()!=null && !c.getDraftUserId().trim().isEmpty()) {
			model.addAttribute(NAVIGATION_ELEMENT_ATTRIBUTE, "drafts");
		} else {
			model.addAttribute(NAVIGATION_ELEMENT_ATTRIBUTE, "collections");
		}
		
		if (auth.isAuth() && c.getSucceedingVersionId()==null && !c.isDeleted()) {
			model.addAttribute("editMode", true);
		}
		
		List<VocabularyItem> collectionTypes = vocabularyItemService.findVocabularyItems(Collection.COLLECTION_TYPES_VOCABULARY_IDENTIFIER);
		List<VocabularyItemViewPojo> collectionTypePojos = vocabularyItemConverter.convertToPojos(collectionTypes, locale);
		Collections.sort(collectionTypePojos);
		
		model.addAttribute("availableCollectionTypes", collectionTypePojos);
		
		List<VocabularyItem> itemTypes = vocabularyItemService.findVocabularyItems(Collection.ITEM_TYPES_VOCABULARY_IDENTIFIER);
		List<VocabularyItemViewPojo> itemTypePojos = vocabularyItemConverter.convertToPojos(itemTypes, locale);
		Collections.sort(itemTypePojos);
		
		model.addAttribute("availableItemTypes", itemTypePojos);
		
		List<VocabularyItem> collectionRelationTypes = vocabularyItemService.findVocabularyItems(CollectionRelation.COLLECTION_RELATION_TYPES_VOCABULARY_IDENTIFIER);
		List<VocabularyItemViewPojo> collectionRelationTypePojos = vocabularyItemConverter.convertToPojos(collectionRelationTypes, locale);
		Collections.sort(collectionRelationTypePojos);
		
		model.addAttribute("availableCollectionRelationTypes", collectionRelationTypePojos);
		
		
		this.setUsers(c);
		
		return "collection/edit";
	}
	
	@RequestMapping(value="{id}/delete", method=RequestMethod.POST)
	public @ResponseBody ModelActionPojo deleteAgent(@PathVariable String id, HttpServletRequest request) {
		AuthPojo auth = authInfoHelper.getAuth(request);
		if (!auth.isAuth()) {
			return new ModelActionPojo(false);
		}
				
		ModelActionPojo result = new ModelActionPojo(false);
		List<Collection> children = collectionService.findCurrentByParentCollectionId(id);
				
		if (children==null || children.size()==0) {
			Collection c = collectionService.findCurrentByCollectionId(id);
			c.setDeleted(true);
			collectionService.save(c, auth.getUserId());
			result.setSuccess(true);
		}
		
		return result;
	}
	
	@RequestMapping(value="query/{query}", method=RequestMethod.GET)
	public @ResponseBody List<Collection> queryCollections(@PathVariable String query, @RequestParam(required=false) List<String> excl) {
		return collectionService.queryCollections(query, excl);
	}
	
	@RequestMapping(method=GET, value={"/includes/editAccess"})
	public String getEditAccessForm(Model model) {
		Access a = new Access();
		model.addAttribute("currIndex", 0);
		model.addAttribute("currMethod", a);
		model.addAttribute("accessMethods[0]", a);
		model.addAttribute("accessTypes", accessTypeService.findAllAccessTypes());
		model.addAttribute("encodingSchemes", schemaService.findAllSchemas());
		model.addAttribute("editMode", true);
		return "collection/edit/incl/edit_access";
	}
	
	@RequestMapping(method=GET, value={"/includes/editAccrual"})
	public String getEditAccrualForm(Model model) {
		Accrual a = new Accrual();
		model.addAttribute("currIndex", 0);
		model.addAttribute("currMethod", a);
		model.addAttribute("accrualMethods[0]", a);
		model.addAttribute("accessTypes", accessTypeService.findAllAccessTypes());
		
		model.addAttribute("accrualMethods", accrualMethodService.findAllAccrualMethods());
		model.addAttribute("accrualPolicies", accrualPolicyService.findAllAccrualPolicies());
		model.addAttribute("accrualPeriodicities", accrualPeriodicityService.findAllAccrualPeriodicities());
		model.addAttribute("editMode", true);
		return "collection/edit/incl/edit_accrual";
	}
	
	@RequestMapping(method=GET, value={"/includes/editAgent"})
	public String getEditAgentForm(Model model) {
		CollectionAgentRelation ar = new CollectionAgentRelation();
		model.addAttribute("currIndex", 0);
		model.addAttribute("currAgentRelation", ar);
		model.addAttribute("agentRelations[0]", ar);
				
		model.addAttribute("agentRelationTypes", agentRelationTypeService.findAllAgentRelationTypes());
		model.addAttribute("editMode", true);
		return "collection/edit/incl/edit_agent";
	}
	
	@RequestMapping(method=GET, value={"/includes/editRelation"})
	public String getEditRelationForm(Model model, Locale locale) {
		model.addAttribute("currIndex", -1);
		model.addAttribute("currRelation", new CollectionRelationViewPojo());
		model.addAttribute("relations[-1]", new CollectionRelation());
		
		List<VocabularyItem> collectionRelationTypes = vocabularyItemService.findVocabularyItems(CollectionRelation.COLLECTION_RELATION_TYPES_VOCABULARY_IDENTIFIER);
		List<VocabularyItemViewPojo> collectionRelationTypePojos = vocabularyItemConverter.convertToPojos(collectionRelationTypes, locale);
		Collections.sort(collectionRelationTypePojos);
		
		model.addAttribute("availableCollectionRelationTypes", collectionRelationTypePojos);
		model.addAttribute("editMode", true);
		return "collection/edit/incl/edit_relation";
	}
	
	@RequestMapping(method=GET, value={"/includes/editDescription"})
	public String getEditDescriptionForm(Model model) {
		LocalizedDescription desc = new LocalizedDescription();
		model.addAttribute("currIndex", 0);
		model.addAttribute("currDesc", desc);
		model.addAttribute("localizedDescriptions[0]", desc);
		model.addAttribute("editMode", true);
		return "collection/edit/incl/edit_description";
	}
	
	@RequestMapping(method=GET, value={"/includes/editItemLanguage"})
	public String getEditItemLanguageForm(Model model) {
		model.addAttribute("currIndex", 0);
		model.addAttribute("currLang", "");
		model.addAttribute("itemLanguages[0]", "");
		return "collection/edit/incl/edit_itemlanguage";
	}
	
	@RequestMapping(method=GET, value={"/includes/editSpatial"})
	public String getEditSpatialForm(Model model) {
		model.addAttribute("currIndex", 0);
		model.addAttribute("currSpat", "");
		model.addAttribute("spatials[0]", "");
		return "collection/edit/incl/edit_spatial";
	}
	
	@RequestMapping(method=GET, value={"/includes/editSubject"})
	public String getEditSubjectForm(Model model) {
		model.addAttribute("currIndex", 0);
		model.addAttribute("currSubj", "");
		model.addAttribute("subjects[0]", "");
		return "collection/edit/incl/edit_subject";
	}
	
	@RequestMapping(method=GET, value={"/includes/editTemporal"})
	public String getEditTemporalForm(Model model) {
		model.addAttribute("currIndex", 0);
		model.addAttribute("currTemp", "");
		model.addAttribute("temporals[0]", "");
		return "collection/edit/incl/edit_temporal";
	}
	
	@RequestMapping(method=GET, value={"/includes/editProvidedIdentifier"})
	public String getEditProvidedIdentifierForm(Model model) {
		model.addAttribute("currIndex", 0);
		model.addAttribute("currIdentifier", "");
		model.addAttribute("providedIdentifier[0]", "");
		return "collection/edit/incl/edit_identifier";
	}
	
	@RequestMapping(method=GET, value={"/includes/editAudience"})
	public String getAudienceForm(Model model) {
		model.addAttribute("currIndex", 0);
		model.addAttribute("currAudi", "");
		model.addAttribute("audiences[0]", "");
		
		return "collection/edit/incl/edit_audience";
	}
	
	@RequestMapping(method=GET, value={"/includes/editLocation"})
	public String getLocationForm(Model model) {
		Address a = new Address();
		model.addAttribute("currIndex", 0);
		model.addAttribute("currAddr", a);
		model.addAttribute("locations[0]", a);
		model.addAttribute("editMode", true);
		return "collection/edit/incl/edit_location";
	}
	
	@RequestMapping(method=GET, value={"/includes/editImage"})
	public String getImageForm(Model model, @RequestParam(name="q") String imageId) {
		
		ImagePojo p = new ImagePojo();
		p.setId(imageId);
		p.setThumbnailUrl((imageService.getImageURI(p.getId(), ImageTypes.THUMBNAIL)));
		p.setImageUrl((imageService.getImageURI(p.getId(), null)));
		
		model.addAttribute("currIndex", 0);
		model.addAttribute("currImage", p);
		model.addAttribute("collectionImages[0]", p);
		model.addAttribute("editMode", true);
		return "collection/edit/incl/edit_image";
	}
	
	@RequestMapping(method=GET, value={"/includes/editCollectionType"})
	public String getEditCollectionTypeForm(Model model, Locale locale) {
		return this.getEditVocabularyItemForm(Collection.COLLECTION_TYPES_VOCABULARY_IDENTIFIER, model, locale);
	}
	
	@RequestMapping(method=GET, value={"/includes/editItemType"})
	public String getEditItemTypeForm(Model model, Locale locale) {
		return this.getEditVocabularyItemForm(Collection.ITEM_TYPES_VOCABULARY_IDENTIFIER, model, locale);
	}
	
	private String getEditVocabularyItemForm(String vocabularyIdentifier, Model model, Locale locale) {
		List<VocabularyItem> vocabularyItems = vocabularyItemService.findVocabularyItems(vocabularyIdentifier);
		List<VocabularyItemViewPojo> vocabularyItemPojos = vocabularyItemConverter.convertToPojos(vocabularyItems, locale);
		Collections.sort(vocabularyItemPojos);
		
		model.addAttribute("vocabularyIdentifier", vocabularyIdentifier);
		
		model.addAttribute("availableVocabularyItems", vocabularyItemPojos);
		model.addAttribute("currIndex", 0);
		model.addAttribute(vocabularyIdentifier + "[0]", "");
		return "collection/edit/incl/edit_vocabulary_item";
	}
	
}