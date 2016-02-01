package eu.dariah.de.colreg.model.base;

import org.joda.time.DateTime;

public abstract class VersionedEntityImpl extends BaseIdentifiable {
	private static final long serialVersionUID = 4943714699707768827L;
	
	private DateTime created;	
	private String creator;
	
	
	public DateTime getCreated() { return created; }
	public void setCreated(DateTime created) { this.created = created; }
	
	public String getCreator() { return creator; }
	public void setCreator(String creator) { this.creator = creator; }
}