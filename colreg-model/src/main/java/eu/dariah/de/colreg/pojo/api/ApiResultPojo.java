package eu.dariah.de.colreg.pojo.api;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.fasterxml.jackson.annotation.JsonInclude;

import eu.dariah.de.colreg.pojo.CollectionApiPojo;

@XmlRootElement(name="result")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlSeeAlso(CollectionApiPojo.class)
public class ApiResultPojo<T> {
	private String apiMethod;
	private boolean success;
	private String message;
	
	@XmlElementWrapper(name="contents")
	@XmlElement(namespace="schema::model", name="collection")
	private Collection<T> content;
	
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
	
	public Collection<T> getContent() { return content; }
	public void setContent(Collection<T> content) { this.content = content; }
}