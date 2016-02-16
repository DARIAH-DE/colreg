package eu.dariah.de.colreg.controller.api;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.dariah.de.colreg.model.api.oaipmh.OaiPmhResponseContainer;
import eu.dariah.de.colreg.model.marshalling.XMLConverter;
import eu.dariah.de.colreg.service.AgentService;
import eu.dariah.de.colreg.service.CollectionService;

@Controller
@RequestMapping("/oaipmh/")
public class OaiPmhController {
	private static DateTimeFormatter datestampFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
	private static DateTimeFormatter timestampFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withLocale(Locale.ROOT).withChronology(ISOChronology.getInstanceUTC());

	@Autowired private AgentService agentService;
	@Autowired private CollectionService collectionService;
	@Autowired private XMLConverter xmlConverter;
	
	
	@RequestMapping(value="", method=RequestMethod.GET, produces={"application/xml"})
	public @ResponseBody Object dispatchOaiPmhCommand(
			@RequestParam(required=false, value="verb") String verb, 
			@RequestParam(required=false, value="identifier") String id, 
			@RequestParam(required=false, value="metadataPrefix") String scheme,
			@RequestParam(required=false, value="from") String from,
			@RequestParam(required=false, value="until") String until,
			@RequestParam(required=false, value="set") String set,
			@RequestParam(required=false, value="resumptionToken") String resumptionToken, 
			HttpServletResponse response) throws IOException {
		
		if (verb==null || !verb.trim().isEmpty()) {
			return this.processError("badVerb", "No verb provided");
		} else if (verb.trim().toLowerCase().equals("getrecord")) {
			return this.processGetRecord(id, scheme);
		} else if (verb.trim().toLowerCase().equals("identify")) {
			return this.processIdentify();
		} else if (verb.trim().toLowerCase().equals("listidentifiers")) {
			return this.processListIdentifiers(from, until, scheme, set, resumptionToken);
		} else if (verb.trim().toLowerCase().equals("listmetadataformats")) {
			return this.processListMetadataFormats(id);
		} else if (verb.trim().toLowerCase().equals("listrecords")) {
			return this.processListRecords(from, until, scheme, set, resumptionToken);
		} else if (verb.trim().toLowerCase().equals("listsets")) {
			return this.processListSets(resumptionToken);
		} else {
			return this.processError("badVerb", String.format("Provided verb [%s] is not covered by OAI-PMH Standard", verb));
		}
	}

	private OaiPmhResponseContainer processError(String code, String message) {
		return null;
	}
	
	private OaiPmhResponseContainer processGetRecord(String id, String scheme) {
		return null;
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
		return timeStamp.toString(timestampFormatter);
	}
	
	private DateTime convertTimeString(String timeString) {
		if (timeString==null || timeString.trim().isEmpty()) {
			return null;
		}
		timeString = timeString.trim().toUpperCase();
		
		// TODO: Match pattern?
		if (timeString.length()==10) {
			return datestampFormatter.parseDateTime(timeString);
		} else if (timeString.length()==20) {
			return timestampFormatter.parseDateTime(timeString);
		}
		return null;
	}
}
