package eu.dariah.de.colreg.model;

import eu.dariah.de.colreg.model.base.BaseIdentifiable;

public class Agent extends BaseIdentifiable {
	private static final long serialVersionUID = 5452333501609638317L;
	
	private String name;
	private String agentType;
	private String foreName;
	private String address;
	private String eMail;
	private String webPage;
	private String phone;
	private String date;
	
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getAgentType() { return agentType; }
	public void setAgentType(String agentType) { this.agentType = agentType; }
	
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
	
	public String getDate() { return date; }
	public void setDate(String date) { this.date = date; }
}