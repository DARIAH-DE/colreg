package eu.dariah.de.colreg.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.CollectionDao;
import eu.dariah.de.colreg.model.Collection;

@Service
public class CollectionServiceImpl implements CollectionService {
	@Autowired private CollectionDao collectionDao;

	@Override
	public Collection createCollection() {
		Collection c = new Collection();
		c.setId("new");
		c.setCollectionId(new ObjectId().toString());
		
		return c;
	}
}
