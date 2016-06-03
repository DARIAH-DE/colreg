package eu.dariah.de.colreg.model.api.repository;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Description", namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#")
public class RepositoryDraft {
	private String aboutAttribute;
	private List<String> identifiers;
	
	private List<String> titles;
	private List<String> descriptions;
	private List<String> contributors;
	

	@XmlAttribute(name="about", namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#")
	public String getAboutAttribute() { return aboutAttribute; }
	public void setAboutAttribute(String aboutAttribute) { this.aboutAttribute = aboutAttribute; }

	@XmlElement(name="identifier", namespace="http://purl.org/dc/elements/1.1/")
	public List<String> getIdentifiers() { return identifiers; }
	public void setIdentifiers(List<String> identifiers) { this.identifiers = identifiers; }
	
	@XmlElement(name="title", namespace="http://purl.org/dc/elements/1.1/")
	public List<String> getTitles() { return titles; }
	public void setTitles(List<String> titles) { this.titles = titles; }

	@XmlElement(name="description", namespace="http://purl.org/dc/elements/1.1/")
	public List<String> getDescriptions() { return descriptions; }
	public void setDescriptions(List<String> descriptions) { this.descriptions = descriptions; }
	
	@XmlElement(name="contributor", namespace="http://purl.org/dc/elements/1.1/")
	public List<String> getContributors() { return contributors; }
	public void setContributors(List<String> contributors) { this.contributors = contributors; }	
}