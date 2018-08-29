package eu.dariah.de.colreg.pojo.api.results;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

import eu.dariah.de.colreg.pojo.api.VocabularyItemApiPojo;

@XmlRootElement(name="result")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VocabularyApiResultPojo extends ApiResultPojo {
	
	@XmlElementWrapper(name="contents")
	@XmlElement(namespace="https://colreg.de.dariah.eu/schemas/DCDDM/2.0/", name="vocabulary_item")
	private Collection<VocabularyItemApiPojo> content;
	
	public Collection<VocabularyItemApiPojo> getContent() { return content; }
	public void setContent(Collection<VocabularyItemApiPojo> content) { this.content = content; }
	
	
	public VocabularyApiResultPojo() { }
	
	public VocabularyApiResultPojo(String apiMethod) {
		this.setApiMethod(apiMethod);
	}
}
