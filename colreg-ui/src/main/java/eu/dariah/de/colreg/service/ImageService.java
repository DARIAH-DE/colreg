package eu.dariah.de.colreg.service;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import eu.dariah.de.colreg.service.ImageServiceImpl.ImageTypes;

public interface ImageService {
	public String getImageURI(String fileId, ImageTypes imageType);
	public File findImage(String name);
	public File findImage(String imageId, ImageTypes imageType);
	
	public void checkAndResizeImage(String name) throws IOException;
	public String importImage(MultipartFile file) throws IOException;
}
