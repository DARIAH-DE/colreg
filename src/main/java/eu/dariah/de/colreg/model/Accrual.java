package eu.dariah.de.colreg.model;

import org.hibernate.validator.constraints.NotBlank;

public class Accrual {
	@NotBlank private String accrualMethod;
	@NotBlank private String accrualPolicy;
	private String description;
	
	
	public String getAccrualMethod() { return accrualMethod; }
	public void setAccrualMethod(String accrualMethod) { this.accrualMethod = accrualMethod; }
	
	public String getAccrualPolicy() { return accrualPolicy; }
	public void setAccrualPolicy(String accrualPolicy) { this.accrualPolicy = accrualPolicy; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
}
