package eu.dariah.de.colreg.service;

import eu.dariah.de.colreg.model.Collection;

public interface CollectionService {
	public Collection createCollection();
	public void save(Collection c);
	public Collection findCurrentByCollectionId(String id);
}
