package eu.dariah.de.colreg.model;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import eu.dariah.de.colreg.model.base.VersionedEntityImpl;
import eu.dariah.de.colreg.model.vocabulary.ItemType;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Collection extends VersionedEntityImpl {
	private static final long serialVersionUID = 6282222176000625940L;
	
	/* Identification */
	private List<String> providedIdentifier;
	
	// Collection description
	@Valid private List<LocalizedDescription> localizedDescriptions;
	
	@NotBlank private String collectionType;
	
	@URL private String webPage;
	@Email private String eMail;
	
	private List<String> audiences;
	private List<String> locations;
	
	private List<String> itemLanguages;
	
	private List<String> subjects;
	private List<String> spatials;
	private List<String> temporals;
	
	private List<String> itemTypeIds;
	
	private String collectionCreated;
	private String itemsCreated;
	
	private Long size;
	
	// Legal information
	@Valid private List<CollectionAgentRelation> agentRelations;
	
	@NotBlank private String collectionDescriptionRights;
	@NotBlank private String accessRights;
	private String itemRights;
	
	// Context	  
	private String parentCollectionId;
	
	private String associatedProject;
	private String provenanceInfo;
	
	// Access and accrual
	@Valid private List<Access> accessMethods;
	@Valid private List<Accrual> accrualMethods;
	
	private boolean deleted;
	private String draftUserId;
		
	public List<String> getProvidedIdentifier() { return providedIdentifier; }
	public void setProvidedIdentifier(List<String> providedIdentifier) { this.providedIdentifier = providedIdentifier; }
	
	public List<LocalizedDescription> getLocalizedDescriptions() { return localizedDescriptions; }
	public void setLocalizedDescriptions(List<LocalizedDescription> localizedDescriptions) { this.localizedDescriptions = localizedDescriptions; }
	
	public String getCollectionType() { return collectionType; }
	public void setCollectionType(String collectionType) { this.collectionType = collectionType; }
	
	public String getWebPage() { return webPage; }
	public void setWebPage(String webPage) { this.webPage = webPage; }
	
	public String getEMail() { return eMail; }
	public void setEMail(String eMail) { this.eMail = eMail; }
	
	public List<String> getItemLanguages() { return itemLanguages; }
	public void setItemLanguages(List<String> itemLanguages) { this.itemLanguages = itemLanguages; }
	
	public Long getSize() { return size; }
	public void setSize(Long size) { this.size = size; }
	
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
	
	public List<String> getItemTypeIds() { return itemTypeIds; }
	public void setItemTypeIds(List<String> itemTypeIds) { this.itemTypeIds = itemTypeIds; }
	
	public List<String> getAudiences() { return audiences; }
	public void setAudiences(List<String> audiences) { this.audiences = audiences; }
	
	public List<String> getLocations() { return locations; }
	public void setLocations(List<String> locations) { this.locations = locations; }
}