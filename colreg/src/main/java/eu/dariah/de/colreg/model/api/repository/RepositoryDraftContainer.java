package eu.dariah.de.colreg.model.api.repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="RDF", namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#")
public class RepositoryDraftContainer {
	
	private RepositoryDraft repositoryDraft;

	@XmlElement(name="Description", namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#")
	public RepositoryDraft getRepositoryDraft() { return repositoryDraft; }
	public void setRepositoryDraft(RepositoryDraft repositoryDraft) { this.repositoryDraft = repositoryDraft; }	
}
