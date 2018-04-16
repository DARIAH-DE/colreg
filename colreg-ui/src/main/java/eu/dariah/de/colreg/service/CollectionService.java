package eu.dariah.de.colreg.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.pojo.ImagePojo;
import eu.dariah.de.colreg.pojo.api.CollectionPojo;
import eu.dariah.de.dariahsp.model.web.AuthPojo;

public interface CollectionService {
	public Collection createCollection(String userId);
	//public void save(Collection c);
	public Collection findCurrentByCollectionId(String id);
	public List<Collection> findAllCurrent();
	public void initializeAgentRelations(Collection c);
	public List<Collection> queryCollections(String query, List<String> excl);
	public List<Collection> findCurrentByParentCollectionId(String id);
	public List<Collection> findCurrentByRelatedAgentId(String id);
	public Collection findCurrentByCollectionId(String id, boolean includeDeleted);
	public void save(Collection c, String userId);
	public List<Collection> findAllVersionsForEntityId(String id);
	public Collection findVersionById(String id, boolean includeDeleted);
	public void appendVersionComment(String versionid, String comment);
	/*public List<CollectionPojo> convertToPojos(List<Collection> collections, Locale locale);
	public <T extends CollectionPojo> List<T> convertToPojos(Class<T> clazz, List<Collection> collections, Locale locale);
	public <T extends CollectionPojo> T convertToPojo(Class<T> clazz, Collection collection, Locale locale);*/
	public List<Collection> findAllDrafts(String userId);
	public List<Collection> findLatestChanges(int i, AuthPojo auth);
	public long countCollections();
	public long countDrafts(String userId);
	public Map<Integer, String> getOrderedImageMap(Map<Integer, String> imageMap);
	//public List<ImagePojo> convertImageMapToPojos(Map<Integer, String> imageMap);
	//public ImagePojo convertImageIdToPojo(String imageId, int index);
}
