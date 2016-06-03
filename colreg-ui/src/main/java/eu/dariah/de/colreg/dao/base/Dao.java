package eu.dariah.de.colreg.dao.base;

import java.util.Collection;
import java.util.List;

public interface Dao {
	public String getCollectionName();
	public Class<?> getClazz();
	
	public List<?> findAll();
	
	/*public void delete(String id);*/
	/*public int delete(Collection<String> id);*/
}
