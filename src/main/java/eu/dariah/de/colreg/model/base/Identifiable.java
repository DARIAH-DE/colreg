package eu.dariah.de.colreg.model.base;

import java.io.Serializable;

public interface Identifiable extends Serializable {
	public String getId();
	public void setId(String id);
}
