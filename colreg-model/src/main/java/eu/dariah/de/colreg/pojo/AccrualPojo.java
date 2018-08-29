package eu.dariah.de.colreg.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

@XmlRootElement(name="accrual")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccrualPojo {
	private String accrualMethod;
	private String accrualPolicy;
	private String accrualPeriodicity;
	
	
	public String getAccrualMethod() { return accrualMethod; }
	public void setAccrualMethod(String accrualMethod) { this.accrualMethod = accrualMethod; }
	
	public String getAccrualPolicy() { return accrualPolicy; }
	public void setAccrualPolicy(String accrualPolicy) { this.accrualPolicy = accrualPolicy; }
	
	public String getAccrualPeriodicity() { return accrualPeriodicity; }
	public void setAccrualPeriodicity(String accrualPeriodicity) { this.accrualPeriodicity = accrualPeriodicity; }
}
