package eu.dariah.de.colreg.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.dariah.de.colreg.controller.base.BaseController;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController {
		
	@RequestMapping("/simple")
	public String testSimple(Model model, Locale locale) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		return "test/simple";
	}
	@RequestMapping("/simpleFluid")
	public String testSimpleFluid(Model model, Locale locale) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		return "test/simpleFluid";
	}
	@RequestMapping("/jumbo")
	public String testJumbo(Model model, Locale locale) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		return "test/jumbo";
	}
	@RequestMapping("/jumboFluid")
	public String testJumboFluid(Model model, Locale locale) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		return "test/jumboFluid";
	}
	@RequestMapping("/main")
	public String testMain(Model model, Locale locale) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		return "test/main";
	}
	@RequestMapping("/mainFluid")
	public String testMainFluid(Model model, Locale locale) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		return "test/mainFluid";
	}
}