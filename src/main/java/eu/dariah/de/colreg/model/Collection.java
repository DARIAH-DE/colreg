package eu.dariah.de.colreg.model;

import java.util.List;
import java.util.Set;

import eu.dariah.de.colreg.model.base.BaseIdentifiable;

public class Collection extends BaseIdentifiable {
	private static final long serialVersionUID = 6282222176000625940L;
	
	private String collectionId;		// Persistent identifier of the collection (id is for the version) 
	private String succeedingVersionId;
	private String parentCollectionId;
	
	private String collectionType;
	private String collectionDescriptionRights;
	private String accessRights;
	private String itemRights;
	
	private String webPage;
	
	private String acronym;
	private Long size;
	
	
	private String associatedProject;
	
	private String accrualPeriodicity;
	private String reproductionPossibilities;
	private String itemEncodingScheme;
	
	
	/* List required */
	private String providedIdentifier;
	private String language; // of the items
	
	private List<CollectionAgentRelation> agentRelations;
	private List<Lang> langs;
	private Set<AccessMethod> accessMethods;
	private Set<Accrual> accruals;
	
	
	public String getCollectionId() { return collectionId; }
	public void setCollectionId(String collectionId) { this.collectionId = collectionId; }
	
	public String getSucceedingVersionId() { return succeedingVersionId; }
	public void setSucceedingVersionId(String succeedingVersionId) { this.succeedingVersionId = succeedingVersionId; }
	
	public String getParentCollectionId() { return parentCollectionId; }
	public void setParentCollectionId(String parentCollectionId) { this.parentCollectionId = parentCollectionId; }
	
	public String getProvidedIdentifier() { return providedIdentifier; }
	public void setProvidedIdentifier(String providedIdentifier) { this.providedIdentifier = providedIdentifier; }
	
	public String getWebPage() { return webPage; }
	public void setWebPage(String webPage) { this.webPage = webPage; }
	
	public String getCollectionType() { return collectionType; }
	public void setCollectionType(String collectionType) { this.collectionType = collectionType; }
	
	public String getAcronym() { return acronym; }
	public void setAcronym(String acronym) { this.acronym = acronym; }
	
	public Long getSize() { return size; }
	public void setSize(Long size) { this.size = size; }
	
	public String getLanguage() { return language; }
	public void setLanguage(String language) { this.language = language; }
	
	public String getCollectionDescriptionRights() { return collectionDescriptionRights; }
	public void setCollectionDescriptionRights(String collectionDescriptionRights) { this.collectionDescriptionRights = collectionDescriptionRights; }
	
	public String getItemRights() { return itemRights; }
	public void setItemRights(String itemRights) { this.itemRights = itemRights; }
	
	public String getAssociatedProject() { return associatedProject; }
	public void setAssociatedProject(String associatedProject) { this.associatedProject = associatedProject; }
	
	public String getAccessRights() { return accessRights; }
	public void setAccessRights(String accessRights) { this.accessRights = accessRights; }
	
	public String getAccrualPeriodicity() { return accrualPeriodicity; }
	public void setAccrualPeriodicity(String accrualPeriodicity) { this.accrualPeriodicity = accrualPeriodicity; }
	
	public String getReproductionPossibilities() { return reproductionPossibilities; }
	public void setReproductionPossibilities(String reproductionPossibilities) { this.reproductionPossibilities = reproductionPossibilities; }
	
	public String getItemEncodingScheme() { return itemEncodingScheme; }
	public void setItemEncodingScheme(String itemEncodingScheme) { this.itemEncodingScheme = itemEncodingScheme; }
	
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	
	public List<CollectionAgentRelation> getAgentRelations() { return agentRelations; }
	public void setAgentRelations(List<CollectionAgentRelation> agentRelations) { this.agentRelations = agentRelations; }
	
	public List<Lang> getLangs() { return langs; }
	public void setLangs(List<Lang> langs) { this.langs = langs; }
	
	public Set<AccessMethod> getAccessMethods() { return accessMethods; }
	public void setAccessMethods(Set<AccessMethod> accessMethods) { this.accessMethods = accessMethods; }
	
	public Set<Accrual> getAccruals() { return accruals; }
	public void setAccruals(Set<Accrual> accruals) { this.accruals = accruals; }
}