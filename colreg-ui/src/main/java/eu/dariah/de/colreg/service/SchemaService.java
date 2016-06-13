package eu.dariah.de.colreg.service;

import java.util.List;

import eu.dariah.de.colreg.model.vocabulary.EncodingScheme;

public interface SchemaService {
	public List<EncodingScheme> findAllSchemas();
}
