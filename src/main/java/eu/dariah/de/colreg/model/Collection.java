package eu.dariah.de.colreg.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import eu.dariah.de.colreg.model.base.VersionedEntityImpl;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Collection extends VersionedEntityImpl {
	private static final long serialVersionUID = 6282222176000625940L;
	
	/* Identification */
	private List<String> providedIdentifier;
	
	// Collection description
	private List<LocalizedDescription> localizedDescriptions;
	private String typeId;
	private String webPage;
	private List<String> itemLanguages; // of the items
	private Long size;
	
	// Legal information
	private List<CollectionAgentRelation> agentRelations;
	private String collectionDescriptionRights;
	private String accessRights;
	private String itemRights;
	
	// Context
	private String parentCollectionId;
	private String associatedProject;
	private String provenanceInfo;
	
	// Access and accrual
	private Set<Access> accessMethods;
	private Set<Accrual> accruals;
	
		
	public List<String> getProvidedIdentifier() { return providedIdentifier; }
	public void setProvidedIdentifier(List<String> providedIdentifier) { this.providedIdentifier = providedIdentifier; }
	
	public List<LocalizedDescription> getLocalizedDescriptions() { return localizedDescriptions; }
	public void setLocalizedDescriptions(List<LocalizedDescription> localizedDescriptions) { this.localizedDescriptions = localizedDescriptions; }
	
	public String getTypeId() { return typeId; }
	public void setTypeId(String typeId) { this.typeId = typeId; }
	
	public String getWebPage() { return webPage; }
	public void setWebPage(String webPage) { this.webPage = webPage; }
	
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
	
	public Set<Access> getAccessMethods() { return accessMethods; }
	public void setAccessMethods(Set<Access> accessMethods) { this.accessMethods = accessMethods; }
	
	public Set<Accrual> getAccruals() { return accruals; }
	public void setAccruals(Set<Accrual> accruals) { this.accruals = accruals; }
}