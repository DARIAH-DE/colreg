package eu.dariah.de.colreg.pojo.converter.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import eu.dariah.de.colreg.model.Agent;
import eu.dariah.de.colreg.pojo.ImagePojo;
import eu.dariah.de.colreg.pojo.converter.ImageConverter;

public abstract class BaseAgentConverter<TPojo> extends BaseConverter<Agent, TPojo> {
	
	@Autowired private ImageConverter imageConverter;
	
	protected String getDisplayName(Agent agent) {
		return agent.getName() + ((agent.getForeName()!=null && !agent.getForeName().trim().isEmpty()) ? ", " + agent.getForeName(): "");
	}
	
	protected ImagePojo getPrimaryImage(Agent agent) {
		if (agent.getAgentImages()!=null && agent.getAgentImages().size()>0) {
			return imageConverter.convertToPojo(agent.getAgentImages().get(0), 0);
		}
		return null;
	}
	
	protected List<ImagePojo> getImages(Agent agent) {
		if (agent.getAgentImages()!=null && agent.getAgentImages().size()>0) {
			return imageConverter.convertToPojos(agent.getAgentImages());
		}
		return null;
	}
	
}
