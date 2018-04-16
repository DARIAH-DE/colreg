package eu.dariah.de.colreg.pojo.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.unibamberg.minf.core.web.service.ImageService;
import de.unibamberg.minf.core.web.service.ImageServiceImpl.ImageTypes;
import eu.dariah.de.colreg.pojo.ImagePojo;

@Component
public class ImageConverter {
	@Autowired private ImageService imageService;
	
	public List<ImagePojo> convertToPojos(Map<Integer, String> imageMap) {
		if (imageMap==null || imageMap.size()==0) {
			return null;
		}
		List<ImagePojo> result = new ArrayList<ImagePojo>();
		for (Integer index : imageMap.keySet()) {
			result.add(this.convertToPojo(imageMap.get(index), index));
		}	
		return result;
	}
	
	public ImagePojo convertToPojo(String imageId) {
		return this.convertToPojo(imageId, -1);
	}
	
	public ImagePojo convertToPojo(String imageId, int index) {
		ImagePojo pImage = new ImagePojo();
		pImage.setIndex(index);
		pImage.setId(imageId);
		pImage.setThumbnailUrl((imageService.getImageURI(pImage.getId(), ImageTypes.THUMBNAIL)));
		pImage.setImageUrl((imageService.getImageURI(pImage.getId(), null)));
		
		return pImage;
	}
}
