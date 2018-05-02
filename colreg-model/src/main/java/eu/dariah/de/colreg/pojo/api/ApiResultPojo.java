package eu.dariah.de.colreg.pojo.api;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

@XmlRootElement(name="result")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResultPojo<T> {
	private String apiMethod;
	private boolean success;
	private String message;
	private T content;
	
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
	
	public T getContent() { return content; }
	public void setContent(T content) { this.content = content; }
}