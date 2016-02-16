package eu.dariah.de.colreg.model.marshalling;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;

/**
 * Facade for XML handling to keep actual implementations exchangeable
 */
public class XMLConverter {

	private Marshaller marshaller;
	private Unmarshaller unmarshaller;

	public Marshaller getMarshaller() { return marshaller; }
 	public void setMarshaller(Marshaller marshaller) { this.marshaller = marshaller; }

	public Unmarshaller getUnmarshaller() { return unmarshaller; }
	public void setUnmarshaller(Unmarshaller unmarshaller) { this.unmarshaller = unmarshaller; }

	
	public String convertObjectToXml(Object obj) throws XmlMappingException, IOException {
		StringWriter sw=null;		
		try {
			sw = new StringWriter();
			this.getMarshaller().marshal(obj, new StreamResult(sw));
		} finally {
			if (sw!=null) {
				sw.close();
				return sw.toString();
			}
		}
		return null;
	}
	
	public Object convertXmlToObject(String xml) throws XmlMappingException, IOException {
		Source source = new StreamSource(new StringReader(xml));
		return unmarshaller.unmarshal(source);
	}
}