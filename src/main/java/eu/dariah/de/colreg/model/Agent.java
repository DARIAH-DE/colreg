package eu.dariah.de.colreg.model;

import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import eu.dariah.de.colreg.model.base.VersionedEntityImpl;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Agent extends VersionedEntityImpl {
	private static final long serialVersionUID = 5452333501609638317L;
	
	@NotBlank
	private String agentTypeId;
	
	@NotBlank
	private String name;
	private String foreName;
	private String address;
	
	@Email
	private String eMail;
	
	@URL
	private String webPage;
	private String phone;
	private List<String> providedIdentifier;
	private String parentAgentId;
	private boolean deleted;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getAgentTypeId() { return agentTypeId; }
	public void setAgentTypeId(String agentTypeId) { this.agentTypeId = agentTypeId; }
	
	public String getForeName() { return foreName; }
	public void setForeName(String foreName) { this.foreName = foreName; }
	
	public String getAddress() { return address; }
	public void setAddress(String address) { this.address = address; }
	
	public String getEMail() { return eMail; }
	public void setEMail(String eMail) { this.eMail = eMail; }
	
	public String getWebPage() { return webPage; }
	public void setWebPage(String webPage) { this.webPage = webPage; }
	
	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }
	
	public List<String> getProvidedIdentifier() { return providedIdentifier; }
	public void setProvidedIdentifier(List<String> providedIdentifier) { this.providedIdentifier = providedIdentifier; }
	
	public String getParentAgentId() { return parentAgentId; }
	public void setParentAgentId(String parentAgentId) { this.parentAgentId = parentAgentId; }
	
	public boolean isDeleted() { return deleted; }
	public void setDeleted(boolean deleted) { this.deleted = deleted; }
}