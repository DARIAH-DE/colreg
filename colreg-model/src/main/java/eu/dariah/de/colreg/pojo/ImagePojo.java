package eu.dariah.de.colreg.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

import eu.dariah.de.colreg.pojo.base.BaseApiPojo;

@XmlRootElement(name="image")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImagePojo extends BaseApiPojo {
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