package eu.dariah.de.colreg.pojo.api;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import eu.dariah.de.colreg.pojo.VocabularyItemApiPojo;

@XmlRootElement(name="result")
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
