package eu.dariah.de.colreg.pojo;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="collection")
public class DcddmCollectionPojo extends CollectionPojo {
	private static final long serialVersionUID = 5030563485613906700L;
	
	private List<AccessPojo> accessPojos;
	private List<AccrualPojo> accrualPojos;
	private String description;
	private Map<String, String> decriptions;
	private String webPage;
	private String eMail;
	
	public List<AccessPojo> getAccessPojos() { return accessPojos; }
	public void setAccessPojos(List<AccessPojo> accessPojos) { this.accessPojos = accessPojos; }
	
	public List<AccrualPojo> getAccrualPojos() { return accrualPojos; }
	public void setAccrualPojos(List<AccrualPojo> accrualPojos) { this.accrualPojos = accrualPojos; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public Map<String, String> getDecriptions() { return decriptions; }
	public void setDecriptions(Map<String, String> decriptions) { this.decriptions = decriptions; }
	
	public String getWebPage() { return webPage; }
	public void setWebPage(String webPage) { this.webPage = webPage; }
	
	public String geteMail() { return eMail; }
	public void seteMail(String eMail) { this.eMail = eMail; }
}
