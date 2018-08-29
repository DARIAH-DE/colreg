package eu.dariah.de.colreg.pojo.api.results;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonInclude;

@XmlRootElement(name="result")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResultPojo {
	private String apiMethod;
	private boolean success;
	private String message;
	
	public ApiResultPojo(String apiMethod) {
		this.apiMethod = apiMethod;
	}
	
	public ApiResultPojo() { }
	
	public String getApiMethod() { return apiMethod; }
	public void setApiMethod(String apiMethod) { this.apiMethod = apiMethod; }

	public boolean isSuccess() { return success; }
	public void setSuccess(boolean success) { this.success = success; }
	
	public String getMessage() { return message; }
	public void setMessage(String message) { this.message = message; }
}