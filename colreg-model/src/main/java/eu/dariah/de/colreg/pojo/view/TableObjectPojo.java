package eu.dariah.de.colreg.pojo.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.unibamberg.minf.dme.model.base.Identifiable;
import eu.dariah.de.colreg.model.base.VersionedEntityImpl;

public class TableObjectPojo<T extends Identifiable> {
	private final T entity;
	
	@JsonProperty(value="DT_RowId")
	private final String rowId;

	public T getEntity() { return entity; }
	public String getRowId() { return rowId; }
	
	public TableObjectPojo(T entity) {
		this.entity = entity;
		if (VersionedEntityImpl.class.isAssignableFrom(entity.getClass())) {
			this.rowId = ((VersionedEntityImpl)entity).getEntityId();
		} else {
			this.rowId = entity.getId();
		}
	}
}