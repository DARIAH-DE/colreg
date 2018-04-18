package eu.dariah.de.colreg.service;

import java.util.List;
import java.util.Map;

import eu.dariah.de.colreg.model.vocabulary.AccessType;

public interface AccessTypeService {
	public List<AccessType> findAllAccessTypes();
	public AccessType findAccessTypeById(String id);
	public AccessType findAccessTypeByIdentfier(String string);
	public Map<String, String> findAccessTypeIdLabelsMap();
}
