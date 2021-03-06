package eu.dariah.de.colreg.dao.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import de.unibamberg.minf.dme.model.base.Identifiable;

public abstract class DaoImpl<T extends Identifiable> implements Dao {
	protected static final String ID_FIELD = "_id";
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected final Class<T> clazz;
	protected final String collectionName;
	
	@Autowired public MongoTemplate mongoTemplate;
	
	@Override public Class<?> getClazz() { return clazz; }
	@Override public String getCollectionName() { return collectionName; }
	
	
	public DaoImpl(Class<?> clazz) {
		this.clazz = (Class<T>)clazz;
		this.collectionName = getCollectionName(clazz);
	}
	
	public DaoImpl(Class<?> clazz, String collectionName) {
		this.clazz = (Class<T>)clazz;
		this.collectionName = collectionName;
	}
	
	public static String getCollectionName(Class<?> clazz) {
		return clazz.getSimpleName().substring(0,1).toLowerCase() + clazz.getSimpleName().substring(1);
	}
}
