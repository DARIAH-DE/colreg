package eu.dariah.de.colreg.pojo.api;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

public class AgentApiPojo extends BaseIdentifiable {
	private static final long serialVersionUID = -5544026876984535637L;

	private String versionId;
	private Long timestamp;
	private String parentId;
	private boolean deleted;
	
	private String type;
	private String name;
	private String lastChanged;
	
	private String localizedTimestamp;
	
	
	public String getVersionId() { return versionId; }
	public void setVersionId(String versionId) { this.versionId = versionId; }
	
	public Long getTimestamp() { return timestamp; }
	public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
	
	public String getLocalizedTimestamp() { return localizedTimestamp; }
	public void setLocalizedTimestamp(String localizedTimestamp) { this.localizedTimestamp = localizedTimestamp; }
	
	public String getParentId() { return parentId; }
	public void setParentId(String parentId) { this.parentId = parentId; }
	
	public boolean isDeleted() { return deleted; }
	public void setDeleted(boolean deleted) { this.deleted = deleted; }
	
	public String getType() { return type; } 
	public void setType(String type) { this.type = type; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getLastChanged() { return lastChanged; }
	public void setLastChanged(String lastChanged) { this.lastChanged = lastChanged; }
}
