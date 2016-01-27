package eu.dariah.de.colreg.dao.base;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;

import eu.dariah.de.colreg.model.base.Identifiable;

public interface BaseDao<T extends Identifiable> extends Dao {
	public List<T> findAll();
	public List<T> findAll(Sort sort);
	public T findById(String id);
	public List<T> find(Query q);
	public T findOne(Query q);
	
	/*public void findAndModify(Query query, Update update);
	public void updateMulti(Query query, Update update);*/
	
	public <S extends T> S save(S entity);
	//public <S extends T> List<S> save(Iterable<S> entites);
	
	public void delete(String id);
	public void delete(T entity);
	public void delete(Iterable<? extends T> entities);
	public int delete(Collection<String> id);
	//public void upsert(Query query, Update update);
}