package eu.dariah.de.colreg.model.base;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.index.Indexed;

public abstract class VersionedEntityImpl extends BaseIdentifiable {
	private static final long serialVersionUID = 4943714699707768827L;
	
	@Indexed private String entityId;		// Persistent identifier of the entity (id is for the version) 
	private String succeedingVersionId;		// The versionId is actually the id of an object 
	private DateTime created;	
	private String creator;
	
	
	public String getEntityId() { return entityId; }
	public void setEntityId(String entityId) { this.entityId = entityId; }
	
	public String getSucceedingVersionId() { return succeedingVersionId; }
	public void setSucceedingVersionId(String succeedingVersionId) { this.succeedingVersionId = succeedingVersionId; }
	
	public DateTime getCreated() { return created; }
	public void setCreated(DateTime created) { this.created = created; }
	
	public String getCreator() { return creator; }
	public void setCreator(String creator) { this.creator = creator; }
}