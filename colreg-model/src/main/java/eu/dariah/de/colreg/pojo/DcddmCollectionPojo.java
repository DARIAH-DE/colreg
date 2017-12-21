package eu.dariah.de.colreg.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="collection")
public class DcddmCollectionPojo extends CollectionPojo {
	private static final long serialVersionUID = 5030563485613906700L;
	
	private List<AccessPojo> accessPojos;
	private List<AccrualPojo> accrualPojos;
	private String imageUrl;
	
	public List<AccessPojo> getAccessPojos() { return accessPojos; }
	public void setAccessPojos(List<AccessPojo> accessPojos) { this.accessPojos = accessPojos; }
	
	public List<AccrualPojo> getAccrualPojos() { return accrualPojos; }
	public void setAccrualPojos(List<AccrualPojo> accrualPojos) { this.accrualPojos = accrualPojos; }
	
	public String getImageUrl() { return imageUrl; }
	public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
