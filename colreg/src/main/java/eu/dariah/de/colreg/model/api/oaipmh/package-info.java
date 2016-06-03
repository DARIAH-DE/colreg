@javax.xml.bind.annotation.XmlSchema(
		namespace = "http://www.openarchives.org/OAI/2.0/", 
		elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED,
		xmlns={ @XmlNs(prefix="", namespaceURI="http://www.openarchives.org/OAI/2.0/"),
				@XmlNs(prefix="xsi", namespaceURI="http://www.w3.org/2001/XMLSchema-instance")}
)
package eu.dariah.de.colreg.model.api.oaipmh;

import javax.xml.bind.annotation.XmlNs;