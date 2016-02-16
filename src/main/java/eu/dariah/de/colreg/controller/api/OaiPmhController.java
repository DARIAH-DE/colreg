package eu.dariah.de.colreg.controller.api;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.dariah.de.colreg.model.Collection;
import eu.dariah.de.colreg.model.api.oaipmh.OaiPmhRecordWrapper;
import eu.dariah.de.colreg.model.api.oaipmh.OaiPmhRequest;
import eu.dariah.de.colreg.model.api.oaipmh.OaiPmhResponseContainer;
import eu.dariah.de.colreg.model.base.VersionedEntityImpl;
import eu.dariah.de.colreg.model.marshalling.XMLConverter;
import eu.dariah.de.colreg.service.AgentService;
import eu.dariah.de.colreg.service.CollectionService;

@Controller
@RequestMapping("/oaipmh/")
public class OaiPmhController {
	public static DateTimeFormatter OAI_DATESTAMP_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
	public static DateTimeFormatter OAI_TIMESTAMP_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withLocale(Locale.ROOT).withChronology(ISOChronology.getInstanceUTC());

	private static final String DCDDM_METADATA_PREFIX = "dcddm";
	
	@Autowired private AgentService agentService;
	@Autowired private CollectionService collectionService;
	@Autowired private XMLConverter xmlConverter;
	@Autowired private Jaxb2Marshaller jaxb2Marshaller;
	
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public @ResponseBody String dispatchOaiPmhCommand(
			@RequestParam(required=false, value="verb") String verb, 
			@RequestParam(required=false, value="identifier") String id, 
			@RequestParam(required=false, value="metadataPrefix") String scheme,
			@RequestParam(required=false, value="from") String from,
			@RequestParam(required=false, value="until") String until,
			@RequestParam(required=false, value="set") String set,
			@RequestParam(required=false, value="resumptionToken") String resumptionToken, 
			HttpServletResponse response) throws IOException {
		
		OaiPmhResponseContainer result;
		
		if (verb==null || verb.trim().isEmpty()) {
			result = this.processError("badVerb", "No verb provided");
		} else if (verb.trim().toLowerCase().equals("getrecord")) {
			result = this.processGetRecord(id, scheme);
			if (result==null) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			} else {
				response.setStatus(HttpServletResponse.SC_OK);
			}
		} else if (verb.trim().toLowerCase().equals("identify")) {
			result = this.processIdentify();
		} else if (verb.trim().toLowerCase().equals("listidentifiers")) {
			result = this.processListIdentifiers(from, until, scheme, set, resumptionToken);
		} else if (verb.trim().toLowerCase().equals("listmetadataformats")) {
			result = this.processListMetadataFormats(id);
		} else if (verb.trim().toLowerCase().equals("listrecords")) {
			result = this.processListRecords(from, until, scheme, set, resumptionToken);
		} else if (verb.trim().toLowerCase().equals("listsets")) {
			result = this.processListSets(resumptionToken);
		} else {
			result = this.processError("badVerb", String.format("Provided verb [%s] is not covered by OAI-PMH Standard", verb));
		}
		
		return xmlConverter.convertObjectToXml(result);
	}

	private OaiPmhResponseContainer processError(String code, String message) {
		return null;
	}
	
	private OaiPmhResponseContainer processGetRecord(String id, String scheme) {
		if (id==null || id.trim().isEmpty()) {
			return this.processError("badArgument", "No identifier provided");
		}
		if (scheme==null || scheme.trim().isEmpty()) {
			return this.processError("badArgument", "No metadataPrefix provided");
		}
		if (!scheme.toLowerCase().trim().equals(DCDDM_METADATA_PREFIX)) {
			return this.processError("cannotDisseminateFormat", String.format("Format [%s] currently not supported", scheme.trim()));
		}
		
		VersionedEntityImpl entity = collectionService.findCurrentByCollectionId(id);
		
		if (entity instanceof Collection && ((Collection)entity).getDraftUserId()!=null) {
			return null;
		} 
		
		OaiPmhResponseContainer container = this.createResponseContainer("GetRecord", id, scheme, null, null, null, null);
		
		//String metadata = "<la>la</lu>";
		container.setRecord(new OaiPmhRecordWrapper(entity, ""));
		
		
		return container;
	}
	
	private OaiPmhResponseContainer processIdentify() {
		return null;
	}
	
	private OaiPmhResponseContainer processListIdentifiers(String from, String until, String scheme, String set, String resumptionToken) {
		return null;
	}
	
	private OaiPmhResponseContainer processListMetadataFormats(String id) {
		return null;
	}
	
	private OaiPmhResponseContainer processListRecords(String from, String until, String scheme, String set, String resumptionToken) {
		return null;
	}
	
	private OaiPmhResponseContainer processListSets(String resumptionToken) {
		return null;
	}
	
	private String convertToTimeString(DateTime timeStamp) {
		return timeStamp.toString(OAI_TIMESTAMP_FORMATTER);
	}
	
	private DateTime convertTimeString(String timeString) {
		if (timeString==null || timeString.trim().isEmpty()) {
			return null;
		}
		timeString = timeString.trim().toUpperCase();
		
		// TODO: Match pattern?
		if (timeString.length()==10) {
			return OAI_DATESTAMP_FORMATTER.parseDateTime(timeString);
		} else if (timeString.length()==20) {
			return OAI_TIMESTAMP_FORMATTER.parseDateTime(timeString);
		}
		return null;
	}
	
	private OaiPmhResponseContainer createResponseContainer(String verb,  String id, String metadataPrefix, String from, String until, String set, String resumptionToken) {
		OaiPmhResponseContainer container = new OaiPmhResponseContainer();
		container.setResponseDate(convertToTimeString(DateTime.now()));
		
		OaiPmhRequest request = new OaiPmhRequest();
		request.setVerb(verb);
		request.setId(id);
		request.setMetadataPrefix(metadataPrefix);
		request.setFrom(from);
		request.setUntil(until);
		request.setSet(set);
		request.setResumptionToken(resumptionToken);
		
		container.setRequest(request);
		return container;
	}
}
