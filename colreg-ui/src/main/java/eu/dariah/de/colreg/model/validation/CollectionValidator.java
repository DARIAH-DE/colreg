package eu.dariah.de.colreg.model.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import eu.dariah.de.colreg.model.Access;
import eu.dariah.de.colreg.model.Address;
import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.CollectionAgentRelation;
import eu.dariah.de.colreg.model.LocalizedDescription;
import eu.dariah.de.colreg.model.vocabulary.AccessType;
import eu.dariah.de.colreg.service.AccessTypeService;
import eu.dariah.de.colreg.service.AgentService;
import eu.dariah.de.colreg.service.CollectionService;
import eu.dariah.de.colreg.service.LanguageService;

@Component
public class CollectionValidator extends BaseValidator<Collection> implements InitializingBean {	
	@Autowired private CollectionService collectionService;
	@Autowired private AgentService agentService;
	@Autowired private LanguageService languageService;
	@Autowired private AccessTypeService accessTypeService;
	
	private String oaiTypeId;
	
	public CollectionValidator() {
		super(Collection.class);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		AccessType oaiType = accessTypeService.findAccessTypeByIdentfier("oaipmh");
		oaiTypeId = oaiType==null ? "" : oaiType.getId();
	}

	@Override
	public void preprocess(Collection collection) {
		
		// Remove the completely empty localized descriptions
		if (collection.getLocalizedDescriptions()!=null && collection.getLocalizedDescriptions().size()>0) {
			List<LocalizedDescription> emptyDescriptions = new ArrayList<LocalizedDescription>();
			for (LocalizedDescription desc : collection.getLocalizedDescriptions()) {
				if (desc.isEmpty()) {
					emptyDescriptions.add(desc);
				}
			}
			collection.getLocalizedDescriptions().removeAll(emptyDescriptions);
		}
		
		// Remove the completely empty locations
		if (collection.getLocations()!=null && collection.getLocations().size()>0) {
			List<Address> emptyAddresses = new ArrayList<Address>();
			for (Address addr : collection.getLocations()) {
				if (addr.isEmpty()) {
					emptyAddresses.add(addr);
				}
			}
			collection.getLocations().removeAll(emptyAddresses);
		}
		
		// Remove empty item languages
		if (collection.getItemLanguages()!=null && collection.getItemLanguages().size()>0) {
			List<String> retainLanguages = new ArrayList<String>();
			for (String lang : collection.getItemLanguages()) {
				if (!lang.trim().isEmpty()) {
					retainLanguages.add(lang.trim());
				}
			}
			collection.setItemLanguages(retainLanguages);
		}
		
		// Remove empty identifiers
		if (collection.getProvidedIdentifier()!=null && collection.getProvidedIdentifier().size()>0) {
			List<String> retainIdentifiers = new ArrayList<String>();
			for (String id : collection.getProvidedIdentifier()) {
				if (id!=null && !id.trim().isEmpty()) {
					retainIdentifiers.add(id);
				}
			}
			collection.setProvidedIdentifier(retainIdentifiers);
		}
		
		// Remove empty audiences
		if (collection.getAudiences()!=null && collection.getAudiences().size()>0) {
			List<String> retainAudiences = new ArrayList<String>();
			for (String id : collection.getAudiences()) {
				if (id!=null && !id.trim().isEmpty()) {
					retainAudiences.add(id);
				}
			}
			collection.setAudiences(retainAudiences);
		}
		
		// Remove empty subjects
		if (collection.getSubjects()!=null && collection.getSubjects().size()>0) {
			List<String> retainSubjects = new ArrayList<String>();
			for (String id : collection.getSubjects()) {
				if (id!=null && !id.trim().isEmpty()) {
					retainSubjects.add(id);
				}
			}
			collection.setSubjects(retainSubjects);
		}
		
		// Remove empty spatials
		if (collection.getSpatials()!=null && collection.getSpatials().size()>0) {
			List<String> retainSpatials = new ArrayList<String>();
			for (String id : collection.getSpatials()) {
				if (id!=null && !id.trim().isEmpty()) {
					retainSpatials.add(id);
				}
			}
			collection.setSpatials(retainSpatials);
		}
		
		// Remove empty temporals
		if (collection.getTemporals()!=null && collection.getTemporals().size()>0) {
			List<String> retainTemporals = new ArrayList<String>();
			for (String id : collection.getTemporals()) {
				if (id!=null && !id.trim().isEmpty()) {
					retainTemporals.add(id);
				}
			}
			collection.setTemporals(retainTemporals);
		}
		
		// Remove duplicate collectionTypes
		if (collection.getCollectionTypes()!=null && collection.getCollectionTypes().size()>0) {
			List<String> containedCollectionTypes = new ArrayList<String>();
			int i=0;
			do {
				if (collection.getCollectionTypes().get(i).isEmpty() || containedCollectionTypes.contains(collection.getCollectionTypes().get(i))) {
					collection.getCollectionTypes().remove(i);
				} else {
					containedCollectionTypes.add(collection.getCollectionTypes().get(i));
					i++;
				}				
			} while (i<collection.getCollectionTypes().size());
		}
		
		// Remove empty agent relations
		if (collection.getAgentRelations()!=null && collection.getAgentRelations().size()>0) {
			List<CollectionAgentRelation> emptyRelations = new ArrayList<CollectionAgentRelation>();
			for (CollectionAgentRelation rel : collection.getAgentRelations()) {
				if (rel.isEmpty()) {
					emptyRelations.add(rel);
				}
			}
			collection.getAgentRelations().removeAll(emptyRelations);
		}
		
		// Access
		if (collection.getAccessMethods()!=null && collection.getAccessMethods().size()>0) {
			Access acc;
			List<String> retainSchemeIds;
			String schemeId;
			for (int i=0; i<collection.getAccessMethods().size(); i++) {
				acc = collection.getAccessMethods().get(i);
				if (acc.getType()!=null && !acc.getType().equals(oaiTypeId)) {
					acc.setOaiSet(null);
				}
				
				// Remove empty encoding schemes
				if (acc.getSchemeIds()!=null && acc.getSchemeIds().size()>0) {
					retainSchemeIds = new ArrayList<String>();
					for (int j=0; j<acc.getSchemeIds().size(); j++) {
						schemeId = acc.getSchemeIds().get(j);
						if (schemeId!=null && !schemeId.trim().isEmpty()) {
							retainSchemeIds.add(schemeId.trim());
						}
					}
					acc.setSchemeIds(retainSchemeIds);
				}
			}
		}
		
		// Prefix http://
		if (collection.getWebPage()!=null && !collection.getWebPage().trim().isEmpty() && 
				!collection.getWebPage().toLowerCase().trim().startsWith("http")) {
			collection.setWebPage("http://" + collection.getWebPage().trim());
		}
	}
	
	@Override
	public void innerValidate(Collection collection, Errors errors) {
		this.validateContact(collection, errors);
		this.validateLocalizedDescriptions(collection, errors);
		this.validateItemLanguages(collection, errors);
		this.validateParentCollection(collection, errors);
		this.validateRelatedAgents(collection, errors);
		this.validateAccess(collection, errors);
		this.validateImage(collection, errors);
		this.validateCollectionTypes(collection, errors);
	}
	
	private void validateCollectionTypes(Collection collection, Errors errors) {
		if (collection.getCollectionTypes()==null || collection.getCollectionTypes().isEmpty()) {
			errors.rejectValue("collectionTypes", "~eu.dariah.de.colreg.validation.collection.type");
		}
	}

	private void validateContact(Collection collection, Errors errors) {
		if ((collection.getWebPage()==null || collection.getWebPage().trim().isEmpty()) && 
				(collection.getEMail()==null || collection.getEMail().trim().isEmpty()) && 
				(collection.getAgentRelations()==null || collection.getAgentRelations().size()==0) && 
				(collection.getLocations()==null || collection.getLocations().size()==0)) {
			errors.rejectValue("webPage", "~eu.dariah.de.colreg.validation.collection.no_contact_specified");
			errors.rejectValue("eMail", "~eu.dariah.de.colreg.validation.collection.no_contact_specified");
			errors.rejectValue("agentRelations", "~eu.dariah.de.colreg.validation.collection.no_contact_specified");
			errors.rejectValue("locations", "~eu.dariah.de.colreg.validation.collection.no_contact_specified");
		}
	}
	
	private void validateLocalizedDescriptions(Collection collection, Errors errors) {
		// One localized description must be present
		if (collection.getLocalizedDescriptions()==null || collection.getLocalizedDescriptions().size()==0) {
			errors.rejectValue("localizedDescriptions", "~eu.dariah.de.colreg.validation.collection.no_description_set");
		}
	}
	
	private void validateItemLanguages(Collection collection, Errors errors) {
		// Any item language must correspond to the defined vocabulary
		if (collection.getItemLanguages()!=null && collection.getItemLanguages().size()>0) {
			boolean itemLanguageError = false;
			for (int i=0; i<collection.getItemLanguages().size(); i++) {
				if (languageService.findLanguageByCode(collection.getItemLanguages().get(i))==null) {
					errors.rejectValue("itemLanguages[" + i + "]", "~eu.dariah.de.colreg.validation.collection.invalid_language");
					itemLanguageError = true;
				}
			}
			
			if (itemLanguageError) {
				errors.rejectValue("itemLanguages", "~eu.dariah.de.colreg.validation.collection.one_or_more_invalid_languages");
			}
		}
	}
	
	private void validateParentCollection(Collection collection, Errors errors) {
		// ParentCollectionId - if set - must relate to valid collection
		if (collection.getParentCollectionId()!=null && !collection.getParentCollectionId().trim().isEmpty()) {
			Collection p = collectionService.findCurrentByCollectionId(collection.getParentCollectionId());
			// TODO: Other reasons why parent collection is invalid -> cycle
			if (p==null) {
				errors.rejectValue("parentCollection", "~eu.dariah.de.colreg.validation.collection.invalid_parent");
			}
		}
	}
	
	private void validateRelatedAgents(Collection collection, Errors errors) {
		// Any related agent id must be valid
		if (collection.getAgentRelations()!=null && collection.getAgentRelations().size()>0) {
			CollectionAgentRelation rel;
			for (int i=0; i<collection.getAgentRelations().size(); i++) {
				rel = collection.getAgentRelations().get(i);
				if (rel.getAgentId()!=null && !rel.getAgentId().trim().isEmpty()) {
					Agent relAgent = agentService.findCurrentByAgentId(rel.getAgentId());
					if (relAgent==null) {
						errors.rejectValue("agentRelations[" + i + "].agentId", "~eu.dariah.de.colreg.validation.collection.invalid_related_agent");
					}
				}
			}
		}
	}
	
	private void validateAccess(Collection collection, Errors errors) {
		if (collection.getAccessMethods()!=null && collection.getAccessMethods().size()>0) {
			Access acc;
			for (int i=0; i<collection.getAccessMethods().size(); i++) {
				acc = collection.getAccessMethods().get(i);
				if (acc.getSchemeIds()!=null && acc.getSchemeIds().size()>0) {
					// TODO: Static list -> nothing to do
					for (int j=0; j<acc.getSchemeIds().size(); j++) {
						//if (vocabularyService.findEncodingSchemeByName(acc.getSchemeIds().get(j))==null) {
							//errors.rejectValue("accessMethods[" + i + "].schemeIds[" + j + "]", "~not_a_valid_scheme");
						//}
					}	
				}
			}
		}
	}
	
	private void validateImage(Collection collection, Errors errors) {
		Map<Integer, String> imageMap = collectionService.getOrderedImageMap(collection.getCollectionImages());
		
		// If an image is set, we need a image rights specification
		if (imageMap!=null && !imageMap.isEmpty()) {
			if (collection.getCollectionImageRights()==null || collection.getCollectionImageRights().trim().isEmpty()) {
				errors.rejectValue("collectionImageRights", "~eu.dariah.de.colreg.validation.collection.need_image_rights");
			}
		}
	}
}
