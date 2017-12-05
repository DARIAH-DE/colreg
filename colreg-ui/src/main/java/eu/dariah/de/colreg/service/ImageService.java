package eu.dariah.de.colreg.service;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	public File findImage(String name);
	public File checkAndResizeImage(String name) throws IOException;
	public File writeFile(MultipartFile file, String name) throws IOException;
}
