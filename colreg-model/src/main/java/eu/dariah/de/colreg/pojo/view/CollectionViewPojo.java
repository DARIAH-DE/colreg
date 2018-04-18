package eu.dariah.de.colreg.pojo.view;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;
import eu.dariah.de.colreg.pojo.ImagePojo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollectionViewPojo extends BaseIdentifiable {
	private static final long serialVersionUID = 110389795212453807L;

	private boolean draft;
	private boolean published;
	private boolean deleted;
	
	private ImagePojo primaryImage;
	private String displayTitle;
	private List<String> collectionTypeIdentifiers;
	private List<String> accessTypes;
	
	private Long timestamp;
	private String displayTimestamp;
	
	
	public boolean isDraft() { return draft; }
	public void setDraft(boolean draft) { this.draft = draft; }
	
	public boolean isPublished() { return published; }
	public void setPublished(boolean published) { this.published = published; }
	
	public boolean isDeleted() { return deleted; }
	public void setDeleted(boolean deleted) { this.deleted = deleted; }
	
	public ImagePojo getPrimaryImage() { return primaryImage; }
	public void setPrimaryImage(ImagePojo primaryImage) { this.primaryImage = primaryImage; }
	
	public String getDisplayTitle() { return displayTitle; }
	public void setDisplayTitle(String displayTitle) { this.displayTitle = displayTitle; }
	
	public List<String> getCollectionTypeIdentifiers() { return collectionTypeIdentifiers; }
	public void setCollectionTypeIdentifiers(List<String> collectionTypeIdentifiers) { this.collectionTypeIdentifiers = collectionTypeIdentifiers; }
	
	public List<String> getAccessTypes() { return accessTypes; }
	public void setAccessTypes(List<String> accessTypes) { this.accessTypes = accessTypes; }
	
	public Long getTimestamp() { return timestamp; }
	public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
	
	public String getDisplayTimestamp() { return displayTimestamp; }
	public void setDisplayTimestamp(String displayTimestamp) { this.displayTimestamp = displayTimestamp; }
}