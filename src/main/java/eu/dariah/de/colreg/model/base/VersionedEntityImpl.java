package eu.dariah.de.colreg.model.base;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.index.Indexed;

public abstract class VersionedEntityImpl extends BaseIdentifiable {
	private static final long serialVersionUID = 4943714699707768827L;
	
	@Indexed 
	private String entityId;				// Persistent identifier of the entity (id is for the version) 
	private String succeedingVersionId;		// The versionId is actually the id of an object 
	
	private DateTime entityTimestamp;	
	private String entityCreator;
	
	private DateTime versionTimestamp;	
	private String versionCreator;
	
	private String versionComment;
	
	public String getEntityId() { return entityId; }
	public void setEntityId(String entityId) { this.entityId = entityId; }
	
	public String getSucceedingVersionId() { return succeedingVersionId; }
	public void setSucceedingVersionId(String succeedingVersionId) { this.succeedingVersionId = succeedingVersionId; }
	
	public DateTime getEntityTimestamp() { return entityTimestamp; }
	public void setEntityTimestamp(DateTime entityTimestamp) { this.entityTimestamp = entityTimestamp; }
	
	public String getEntityCreator() { return entityCreator; }
	public void setEntityCreator(String entityCreator) { this.entityCreator = entityCreator; }
	
	public DateTime getVersionTimestamp() { return versionTimestamp; }
	public void setVersionTimestamp(DateTime versionTimestamp) { this.versionTimestamp = versionTimestamp; }
	
	public String getVersionCreator() { return versionCreator; }
	public void setVersionCreator(String versionCreator) { this.versionCreator = versionCreator; }
	
	public String getVersionComment() { return versionComment; }
	public void setVersionComment(String versionComment) { this.versionComment = versionComment; }	
}