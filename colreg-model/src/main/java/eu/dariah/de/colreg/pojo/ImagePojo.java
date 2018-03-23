package eu.dariah.de.colreg.pojo;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class ImagePojo extends BaseIdentifiable {
	private static final long serialVersionUID = 3821489866959703563L;
	
	private int index;
	private String imageUrl;
	private String thumbnailUrl;
	
	public int getIndex() { return index; }
	public void setIndex(int index) { this.index = index; }
	
	public String getImageUrl() { return imageUrl; }
	public void setImageUrl(String imageUrl) {  this.imageUrl = imageUrl;} 
	
	public String getThumbnailUrl() { return thumbnailUrl; }
	public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
}