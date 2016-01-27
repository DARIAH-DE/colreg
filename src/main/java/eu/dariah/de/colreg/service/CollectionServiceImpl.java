package eu.dariah.de.colreg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.dariah.de.colreg.dao.CollectionDao;

@Service
public class CollectionServiceImpl implements CollectionService {
	@Autowired private CollectionDao collectionDao;
}
