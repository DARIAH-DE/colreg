package eu.dariah.de.colreg.controller.exceptions;

import javax.activity.InvalidActivityException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.unibamberg.minf.core.web.controller.ResourceNotFoundException;
import de.unibamberg.minf.core.web.localization.MessageSource;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;

@ControllerAdvice
public class SampleExceptionHandler {
	protected static final Logger logger = LoggerFactory.getLogger(SampleExceptionHandler.class);

	public static final String DEFAULT_ERROR_VIEW = "error";

	@ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(InvalidActivityException.class)
    public void handleConflict() {
        // Nothing to do, just to show how individual errors could be handled
    }
	
	@ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(Model m, HttpServletRequest req, Exception e) {
        m.addAttribute("url", req.getRequestURL());
		m.addAttribute("exception", e);

		logger.error("Caught server runtime (404) error:", e); 
		
		return "error/404";
		
    }
	
	@ExceptionHandler(value = Exception.class)
	public String defaultErrorHandler(Model m, HttpServletRequest req, Exception e) throws Exception {
		// If the exception is annotated with @ResponseStatus rethrow it and let
		// the framework handle it - like the OrderNotFoundException example
		// at the start of this post.
		// AnnotationUtils is a Spring Framework utility class.
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
			throw e;
		}

		// Otherwise setup and send the user to a default error-view.
		m.addAttribute("errorHeading", "An internal server error has occurred");
		m.addAttribute("errorMsg", e.getMessage());
		m.addAttribute("url", req.getRequestURL());
		m.addAttribute("exception", e);

		logger.error("Caught server runtime (500) error:", e); 
		
		return DEFAULT_ERROR_VIEW;
	}
}
