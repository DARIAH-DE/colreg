package eu.dariah.de.colreg.pojo.api.results;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.fasterxml.jackson.annotation.JsonInclude;

import eu.dariah.de.colreg.pojo.api.CollectionApiPojo;
import eu.dariah.de.colreg.pojo.api.ExtendedCollectionApiPojo;

@XmlRootElement(name="result")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(ExtendedCollectionApiPojo.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollectionApiResultPojo<T extends CollectionApiPojo> extends ApiResultPojo {

	@XmlElementWrapper(name="contents")
	@XmlElement(namespace="https://colreg.de.dariah.eu/schemas/DCDDM/2.0/", name="collection")
	private Collection<T> content;
	
	public Collection<T> getContent() { return content; }
	public void setContent(Collection<T> content) { this.content = content; }
	
	
	public CollectionApiResultPojo() { }
	
	public CollectionApiResultPojo(String apiMethod) {
		this.setApiMethod(apiMethod);
	}
}
