package eu.dariah.de.colreg.pojo.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import de.unibamberg.minf.dme.model.base.BaseIdentifiable;

@XmlAccessorType(XmlAccessType.FIELD)
public class BaseApiPojo extends BaseIdentifiable {
	private static final long serialVersionUID = -5862938285095958441L;

	@XmlElement(name="id")
	@Override
	public String getId() { return super.getId(); }
}
