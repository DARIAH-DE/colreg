package eu.dariah.de.colreg.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

import eu.dariah.de.colreg.pojo.base.BaseApiPojo;

@XmlRootElement(name="vocabulary")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VocabularyPojo extends BaseApiPojo {
	private static final long serialVersionUID = -5301800695287247433L;
	
	private String identifier;
	private String localizedLabel;
	
	
	public String getIdentifier() { return identifier; }
	public void setIdentifier(String identifier) { this.identifier = identifier; }
	
	public String getLocalizedLabel() { return localizedLabel; }
	public void setLocalizedLabel(String localizedLabel) { this.localizedLabel = localizedLabel; }
}