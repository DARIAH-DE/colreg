package eu.dariah.de.colreg.pojo;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

@XmlRootElement(name="collection")
public class CollectionPojo extends BaseIdentifiable {
	private static final long serialVersionUID = -5544026876984535637L;

	private String entityId;
	private String versionId;
	private Long versionTimestamp;
	private String parentEntityId;
	private String state;

	private String title;
	private String acronym;
	
	private String thumbnailUrl;
	private String imageUrl;

	private Map<String, String> titles;
	private Map<String, String> acronyms;
	private String access;
	private String lastChanged;
	
	private List<AgentPojo> agents;
	private List<String> collectionTypes;
	
	
	@XmlElement(name="id")
	@Override
	public String getId() { return super.getId(); }
	
	public String getVersionId() { return versionId; }
	public void setVersionId(String versionId) { this.versionId = versionId; }
	
	public Long getVersionTimestamp() { return versionTimestamp; }
	public void setVersionTimestamp(Long versionTimestamp) { this.versionTimestamp = versionTimestamp; }
	
	public String getEntityId() { return entityId; }
	public void setEntityId(String entityId) { this.entityId = entityId; }
	
	public String getParentEntityId() { return parentEntityId; }
	public void setParentEntityId(String parentEntityId) { this.parentEntityId = parentEntityId; }
	
	public String getState() { return state; }
	public void setState(String state) { this.state = state; }
		
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	
	public String getAcronym() { return acronym; }
	public void setAcronym(String acronym) { this.acronym = acronym; }
	
	public Map<String, String> getTitles() { return titles; }
	public void setTitles(Map<String, String> titles) { this.titles = titles; }
	
	public Map<String, String> getAcronyms() { return acronyms; }
	public void setAcronyms(Map<String, String> acronyms) { this.acronyms = acronyms; }

	public String getThumbnailUrl() { return thumbnailUrl; }
	public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

	public String getImageUrl() { return imageUrl; }
	public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

	public String getAccess() { return access; }
	public void setAccess(String access) { this.access = access; }
	
	public String getLastChanged() { return lastChanged; }
	public void setLastChanged(String lastChanged) { this.lastChanged = lastChanged; }

	public List<AgentPojo> getAgents() { return agents; }
	public void setAgents(List<AgentPojo> agents) { this.agents = agents; }

	public List<String> getCollectionTypes() { return collectionTypes; }
	public void setCollectionTypes(List<String> collectionTypes) { this.collectionTypes = collectionTypes; }
}
