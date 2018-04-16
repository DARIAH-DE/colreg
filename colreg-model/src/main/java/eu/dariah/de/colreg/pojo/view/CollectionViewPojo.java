package eu.dariah.de.colreg.pojo.view;

import java.util.List;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class CollectionViewPojo extends BaseIdentifiable {
	private static final long serialVersionUID = 110389795212453807L;

	private String state;
	private String thumbnailUrl;
	private String displayTitle;
	private List<String> collectionTypeIdentifiers;
	private List<String> accessTypes;
	
	private Long timestamp;
	private String displayTimestamp;
	
	
	public String getState() { return state; }
	public void setState(String state) { this.state = state; }
	
	public String getThumbnailUrl() { return thumbnailUrl; }
	public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
	
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