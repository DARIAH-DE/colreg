package eu.dariah.de.colreg.model;

import java.util.List;
import java.util.Map;

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
	
	@NotBlank(message="{~eu.dariah.de.colreg.validation.agent.name}")
	private String name;
	private String foreName;
	
	private List<Address> addresses;
	
	@Email(message="{~eu.dariah.de.colreg.validation.agent.email}")
	private String eMail;
	
	@URL(message="{~eu.dariah.de.colreg.validation.agent.webpage}")
	private String webPage;
	private String phone;
	
	private String gndId;
	
	private List<String> providedIdentifier;
	private String parentAgentId;
	private boolean deleted;
	
	private Map<Integer, String> agentImages;
	private String agentImageRights;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getAgentTypeId() { return agentTypeId; }
	public void setAgentTypeId(String agentTypeId) { this.agentTypeId = agentTypeId; }
	
	public String getForeName() { return foreName; }
	public void setForeName(String foreName) { this.foreName = foreName; }
	
	public List<Address> getAddresses() { return addresses; }
	public void setAddresses(List<Address> addresses) { this.addresses = addresses; }
	
	public String getEMail() { return eMail; }
	public void setEMail(String eMail) { this.eMail = eMail; }
	
	public String getWebPage() { return webPage; }
	public void setWebPage(String webPage) { this.webPage = webPage; }
	
	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }
	
	public String getGndId() { return gndId; }
	public void setGndId(String gndId) { this.gndId = gndId; }
	
	public List<String> getProvidedIdentifier() { return providedIdentifier; }
	public void setProvidedIdentifier(List<String> providedIdentifier) { this.providedIdentifier = providedIdentifier; }
	
	public String getParentAgentId() { return parentAgentId; }
	public void setParentAgentId(String parentAgentId) { this.parentAgentId = parentAgentId; }
	
	public boolean isDeleted() { return deleted; }
	public void setDeleted(boolean deleted) { this.deleted = deleted; }
	
	public Map<Integer, String> getAgentImages() { return agentImages; }
	public void setAgentImages(Map<Integer, String> agentImages) { this.agentImages = agentImages; }
	
	public String getAgentImageRights() { return agentImageRights; }
	public void setAgentImageRights(String agentImageRights) { this.agentImageRights = agentImageRights; }
}