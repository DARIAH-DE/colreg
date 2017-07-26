package eu.dariah.de.colreg.pojo;

import java.util.ArrayList;
import java.util.List;

import eu.dariah.de.minfba.core.metamodel.interfaces.Identifiable;



public class TableListPojo<T extends Identifiable> {
	private List<TableObjectPojo<T>> aaData;

	public List<TableObjectPojo<T>> getAaData() { return aaData; }
	public void setAaData(List<TableObjectPojo<T>> aaData) { this.aaData = aaData; }
	
	public TableListPojo(List<T> data) {
		if (data==null) {
			return;
		}
		
		this.aaData = new ArrayList<TableObjectPojo<T>>(data.size());
		TableObjectPojo<T> wrappedEntity;
		for (T entity : data) {
			wrappedEntity = new TableObjectPojo<T>(entity);
			this.aaData.add(wrappedEntity);
		}
	}
}
