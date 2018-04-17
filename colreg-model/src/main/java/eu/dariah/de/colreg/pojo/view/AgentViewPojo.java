package eu.dariah.de.colreg.pojo.view;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class AgentViewPojo extends BaseIdentifiable {
	private static final long serialVersionUID = -5544026876984535637L;

	private boolean deleted;
	private Long timestamp;
	private String displayTimestamp;
	private String type;
	private String name;
	
	
	public boolean isDeleted() { return deleted; }
	public void setDeleted(boolean deleted) { this.deleted = deleted; }
	
	public Long getTimestamp() { return timestamp; }
	public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
	
	public String getDisplayTimestamp() { return displayTimestamp; }
	public void setDisplayTimestamp(String displayTimestamp) { this.displayTimestamp = displayTimestamp; }
	
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	
	public String getName() {  return name; }
	public void setName(String name) { this.name = name; }
}