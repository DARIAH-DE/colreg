package eu.dariah.de.colreg.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/api/vocabulary")
public class VocabularyApiController {
	protected static final Logger logger = LoggerFactory.getLogger(VocabularyApiController.class);
}
