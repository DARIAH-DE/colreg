package eu.dariah.de.colreg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.dariah.de.colreg.service.AgentService;

@Controller
@RequestMapping("/agent")
public class AgentController {
	@Autowired private AgentService agentService;
}