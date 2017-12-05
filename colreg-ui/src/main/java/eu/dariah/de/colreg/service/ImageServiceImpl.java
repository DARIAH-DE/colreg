package eu.dariah.de.colreg.service;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageServiceImpl implements ImageService, InitializingBean {
	protected static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
	
	@Value("${paths.images}")
	private String imagePath;
	
	@Value("${paths.images_backup:#{null}}")
	private String imageBackupPath;
	
	@Value("${images.width:150}") 
	private int imagesWidth;
	
	@Value("${images.height:150}") 
	private int imagesHeight;
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (imageBackupPath==null) {
			imageBackupPath = imagePath + File.separator + "backup";
		}
		
		Files.createDirectories(Paths.get(new File(imagePath).toURI()), new FileAttribute<?>[0]);
		Files.createDirectories(Paths.get(new File(imageBackupPath).toURI()), new FileAttribute<?>[0]);
	}
	
	@Override
	public File findImage(String name) {
		File imageDir = new File(imagePath);
		for (File f : imageDir.listFiles()) {
			if (f.getName().equals(name) || f.getName().startsWith(name + ".")) {
				return f;
			}
		}
		return null;
	}
	
	@Override
	public File checkAndResizeImage(String name) throws IOException {
		File importFile = this.findImage(name);
		
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(importFile);

		    Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
		    ImageReader reader = null;
		    BufferedImage image = null;
			while (imageReaders.hasNext()) {
			    reader = (ImageReader) imageReaders.next();
			    reader.setInput(iis);
			    image = reader.read(0);	
			}
			if (image==null) {
		        throw new IOException("The file could not be opened, it is not an image");
		    }
			
		    String ext = reader.getFormatName().toLowerCase();
		    String newPath = importFile.getAbsolutePath().toString() + "." + ext;
		    
		    Files.move(Paths.get(importFile.getAbsolutePath().toString()), Paths.get(newPath), StandardCopyOption.REPLACE_EXISTING);
		    Files.copy(Paths.get(newPath), Paths.get(imageBackupPath + importFile.getName()), StandardCopyOption.REPLACE_EXISTING);
		    this.resizeImage(image, ext, newPath);		    
		    return new File(newPath);
		    
		} catch(IOException ex) {
			throw new IOException("The file could not be opened, an error occurred", ex);
		}
	}
	
	private void resizeImage(BufferedImage originalImage, String format, String path) throws IOException{
		if (format.toLowerCase().equals("jpeg")) {
			format = "jpg";
		}
		
		int imageType = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		int height;
		int width;
		
		if ((double)originalImage.getHeight() <= imagesHeight && (double)originalImage.getWidth() <= imagesWidth) {
			return;
		}
		
		if ( ((double)imagesHeight / (double)originalImage.getHeight() ) <
			  ((double)imagesWidth / (double)originalImage.getWidth() ) ) {
			height = imagesHeight;
			width = (int)Math.floor((double)imagesHeight / (double)originalImage.getHeight() * originalImage.getWidth());
		} else {
			height = (int)Math.floor((double)imagesWidth / (double)originalImage.getWidth() * originalImage.getHeight());
			width = imagesWidth;
		}
		
		
		BufferedImage resizedImage = new BufferedImage(width, height, imageType);
		
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();	
		g.setComposite(AlphaComposite.Src);
	 
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	 
		ImageIO.write(resizedImage, format, new File(path)); 
	}

	@Override
	public File writeFile(MultipartFile file, String name) throws IOException {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		File result = new File(imagePath + File.separator + name);
		
		try {
			fos = new FileOutputStream(result);
			bos = new BufferedOutputStream(fos);
			bos.write(file.getBytes());
			
			return result;
		} catch (IOException e) {
			logger.error("Failed to write file", e);
			throw e;
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e) {}
			}
		}
	}
	
}
