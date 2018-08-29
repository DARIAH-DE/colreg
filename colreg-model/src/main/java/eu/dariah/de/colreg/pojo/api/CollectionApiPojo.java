package eu.dariah.de.colreg.pojo.api;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

import eu.dariah.de.colreg.pojo.ImagePojo;
import eu.dariah.de.colreg.pojo.base.BaseApiPojo;

@XmlRootElement(name="collection")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollectionApiPojo extends BaseApiPojo {
	private static final long serialVersionUID = -5544026876984535637L;

	private String versionId;
	private Long timestamp;
	private String parentId;
	
	private boolean draft;
	private boolean published;
	private boolean deleted;
	
	private ImagePojo primaryImage;

	/* If locale for conversion is provided */
	private String localizedTitle;
	private String localizedAcronym;
	private String localizedTimestamp;
	
	/* If no locale for conversion is provided */
	private Map<String, String> titles;
	private Map<String, String> acronyms;
	
	@XmlElementWrapper(name="agents")
	@XmlElement(name="agent")
	private List<AgentApiPojo> agents;
	
	@XmlElementWrapper(name="collection_types")
	@XmlElement(name="type")
	private List<String> collectionTypes;
	
	
	public String getVersionId() { return versionId; }
	public void setVersionId(String versionId) { this.versionId = versionId; }

	public Long getTimestamp() { return timestamp; }
	public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

	public String getLocalizedTimestamp() { return localizedTimestamp; }
	public void setLocalizedTimestamp(String localizedTimestamp) { this.localizedTimestamp = localizedTimestamp; }

	public String getParentId() { return parentId; }
	public void setParentId(String parentId) { this.parentId = parentId; }

	public boolean isDraft() { return draft; }
	public void setDraft(boolean draft) { this.draft = draft; }

	public boolean isPublished() { return published; }
	public void setPublished(boolean published) { this.published = published; }

	public boolean isDeleted() { return deleted; }
	public void setDeleted(boolean deleted) { this.deleted = deleted; }

	public ImagePojo getPrimaryImage() { return primaryImage; }
	public void setPrimaryImage(ImagePojo primaryImage) { this.primaryImage = primaryImage; }

	public String getLocalizedTitle() { return localizedTitle; }
	public void setLocalizedTitle(String localizedTitle) { this.localizedTitle = localizedTitle; }

	public String getLocalizedAcronym() { return localizedAcronym; }
	public void setLocalizedAcronym(String localizedAcronym) { this.localizedAcronym = localizedAcronym; }

	public Map<String, String> getTitles() { return titles; }
	public void setTitles(Map<String, String> titles) { this.titles = titles; }

	public Map<String, String> getAcronyms() { return acronyms; }
	public void setAcronyms(Map<String, String> acronyms) { this.acronyms = acronyms; }

	public List<AgentApiPojo> getAgents() { return agents; }
 	public void setAgents(List<AgentApiPojo> agents) { this.agents = agents; }

	public List<String> getCollectionTypes() {  return collectionTypes; }
 	public void setCollectionTypes(List<String> collectionTypes) { this.collectionTypes = collectionTypes; }
}