package eu.dariah.de.colreg.model;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import eu.dariah.de.colreg.model.base.VersionedEntityImpl;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)


@XmlRootElement(name="collection")
public class Collection extends VersionedEntityImpl {
	private static final long serialVersionUID = 6282222176000625940L;
	
	public static final String COLLECTION_TYPES_VOCABULARY_IDENTIFIER = "collectionTypes";
	
	/* Identification */
	private List<String> providedIdentifier;
	
	// Collection description
	@Valid private List<LocalizedDescription> localizedDescriptions;
	

	
	@URL(message="{~eu.dariah.de.colreg.validation.collection.webpage}")
	private String webPage;
	
	@Email(message="{~eu.dariah.de.colreg.validation.collection.email}")
	private String eMail;
	
	private List<String> audiences;
	private List<Address> locations;
	
	private List<String> collectionTypes;
	private List<String> itemLanguages;
	
	private List<String> subjects;
	private List<String> spatials;
	private List<String> temporals;
	
	private List<String> itemTypeIds;
	
	private String collectionCreated;
	private String itemsCreated;
	
	private Long size;
	private String uomId;
	
	private Map<Integer, String> collectionImages;
	private String collectionImageRights;
	
	// Legal information
	@Valid private List<CollectionAgentRelation> agentRelations;
	
	@NotBlank(message="{~eu.dariah.de.colreg.validation.collection.collection_description_rights}")
	private String collectionDescriptionRights;
	
	@NotBlank(message="{~eu.dariah.de.colreg.validation.collection.access_rights}")
	private String accessRights;
	private String itemRights;
	
	// Context	  
	private String parentCollectionId;
	
	private String associatedProject;
	private String provenanceInfo;
	
	// Access and accrual
	@Valid private List<Access> accessMethods;
	@Valid private List<Accrual> accrualMethods;
	
	private boolean researchDriven;
	private boolean curationDriven;
	private String accrualDescription;
	
	private boolean deleted;
	private String draftUserId;
		
	public List<String> getCollectionTypes() { return collectionTypes; }
	public void setCollectionTypes(List<String> collectionTypes) { this.collectionTypes = collectionTypes; }
	
	public List<String> getProvidedIdentifier() { return providedIdentifier; }
	public void setProvidedIdentifier(List<String> providedIdentifier) { this.providedIdentifier = providedIdentifier; }
	
	public List<LocalizedDescription> getLocalizedDescriptions() { return localizedDescriptions; }
	public void setLocalizedDescriptions(List<LocalizedDescription> localizedDescriptions) { this.localizedDescriptions = localizedDescriptions; }
	

	public String getWebPage() { return webPage; }
	public void setWebPage(String webPage) { this.webPage = webPage; }
	
	public String getEMail() { return eMail; }
	public void setEMail(String eMail) { this.eMail = eMail; }
	
	public List<String> getItemLanguages() { return itemLanguages; }
	public void setItemLanguages(List<String> itemLanguages) { this.itemLanguages = itemLanguages; }
	
	public Long getSize() { return size; }
	public void setSize(Long size) { this.size = size; }
	
	public String getUomId() { return uomId; }
	public void setUomId(String uomId) { this.uomId = uomId; }
	
	public List<CollectionAgentRelation> getAgentRelations() { return agentRelations; }
	public void setAgentRelations(List<CollectionAgentRelation> agentRelations) { this.agentRelations = agentRelations; }
	
	public String getCollectionDescriptionRights() { return collectionDescriptionRights; }
	public void setCollectionDescriptionRights(String collectionDescriptionRights) { this.collectionDescriptionRights = collectionDescriptionRights; }
	
	public String getAccessRights() { return accessRights; }
	public void setAccessRights(String accessRights) { this.accessRights = accessRights; }
	
	public String getItemRights() { return itemRights; }
	public void setItemRights(String itemRights) { this.itemRights = itemRights; }
	
	public String getParentCollectionId() { return parentCollectionId; }
	public void setParentCollectionId(String parentCollectionId) { this.parentCollectionId = parentCollectionId; }
	
	public String getAssociatedProject() { return associatedProject; }
	public void setAssociatedProject(String associatedProject) { this.associatedProject = associatedProject; }
	
	public String getProvenanceInfo() { return provenanceInfo; }
	public void setProvenanceInfo(String provenanceInfo) { this.provenanceInfo = provenanceInfo; }
	
	public List<Access> getAccessMethods() { return accessMethods; }
	public void setAccessMethods(List<Access> accessMethods) { this.accessMethods = accessMethods; }
	
	public List<Accrual> getAccrualMethods() { return accrualMethods; }
	public void setAccrualMethods(List<Accrual> accrualMethods) { this.accrualMethods = accrualMethods; }
	
	public boolean isResearchDriven() { return researchDriven; }
	public void setResearchDriven(boolean researchDriven) { this.researchDriven = researchDriven; }
	
	public boolean isCurationDriven() { return curationDriven; }
	public void setCurationDriven(boolean curationDriven) { this.curationDriven = curationDriven; }
	
	public String getAccrualDescription() { return accrualDescription; }
	public void setAccrualDescription(String accrualDescription) { this.accrualDescription = accrualDescription; }
	
	public boolean isDeleted() { return deleted; }
	public void setDeleted(boolean deleted) { this.deleted = deleted; }
	
	public String getDraftUserId() { return draftUserId; }
	public void setDraftUserId(String draftUserId) { this.draftUserId = draftUserId; }
	
	public List<String> getSubjects() {	return subjects; }
	public void setSubjects(List<String> subjects) { this.subjects = subjects; }
	
	public List<String> getSpatials() { return spatials; }
	public void setSpatials(List<String> spatials) { this.spatials = spatials; }
	
	public List<String> getTemporals() { return temporals; }
	public void setTemporals(List<String> temporals) { this.temporals = temporals; }
	
	public String getCollectionCreated() { return collectionCreated; }
	public void setCollectionCreated(String collectionCreated) { this.collectionCreated = collectionCreated; }
	
	public String getItemsCreated() { return itemsCreated; }
	public void setItemsCreated(String itemsCreated) { this.itemsCreated = itemsCreated; }
	
	public Map<Integer, String> getCollectionImages() { return collectionImages; }
	public void setCollectionImages(Map<Integer, String> collectionImages) { this.collectionImages = collectionImages; }
	
	public String getCollectionImageRights() { return collectionImageRights; }
	public void setCollectionImageRights(String collectionImageRights) { this.collectionImageRights = collectionImageRights; }
	
	public List<String> getItemTypeIds() { return itemTypeIds; }
	public void setItemTypeIds(List<String> itemTypeIds) { this.itemTypeIds = itemTypeIds; }
	
	public List<String> getAudiences() { return audiences; }
	public void setAudiences(List<String> audiences) { this.audiences = audiences; }
	
	public List<Address> getLocations() { return locations; }
	public void setLocations(List<Address> locations) { this.locations = locations; }
}