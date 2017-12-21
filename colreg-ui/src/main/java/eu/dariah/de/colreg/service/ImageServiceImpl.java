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
import java.nio.file.attribute.FileAttribute;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class ImageServiceImpl implements ImageService, InitializingBean {
	protected static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
		
	public enum ImageTypes {
		ORIGINAL,
		DISPLAY,
		THUMBNAIL;
	}
	
	@Value("${paths.images}")
	private String imagePath;
	
	@Value("${images.width:1000}") 
	private int imagesWidth;
	
	@Value("${images.height:1000}") 
	private int imagesHeight;
	
	@Value("${images.thumbnails.width:150}") 
	private int thumbnailsWidth;
	
	@Value("${images.thumbnails.height:150}") 
	private int thumbnailsHeight;
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Files.createDirectories(Paths.get(new File(imagePath).toURI()), new FileAttribute<?>[0]);
	}
	
	@Override
	public String getImageURI(String fileId, ImageTypes imageType) {
		if (fileId==null) {
			return null;
		}
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentServletMapping();
		if (imageType==null) {
			builder.path("/image/" + fileId);
		} else {
			builder.path("/image/" + fileId + "/" + imageType.toString());
		}
		
		return builder.build().toUri().toString();
	}
	
	@Override
	public String importImage(MultipartFile file) throws IOException {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		if (file==null) {
			return null;
		}
		
		String fileId = new ObjectId().toString();
		int extIndex = file.getOriginalFilename().lastIndexOf(".");
		
		String ext = "";
		if (extIndex>0) {
			ext = file.getOriginalFilename().substring(extIndex); 
		}
		
		File result = new File(String.format("%s%s%s%s%s%s", imagePath, File.separator, fileId, File.separator, ImageTypes.ORIGINAL.toString(), ext));
		FileUtils.forceMkdirParent(result);
		
		try {
			fos = new FileOutputStream(result);
			bos = new BufferedOutputStream(fos);
			bos.write(file.getBytes());
			
			return fileId;
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
	
	@Override
	public File findImage(String name) {
		return this.findImage(name, null);
	}
	
	@Override
	public File findImage(String name, ImageTypes imageType) {
		if (name==null) {
			return null;
		}
		
		File imageDir = new File(imagePath + File.separator + name);
		if (imageType==null) {
			imageType = ImageTypes.DISPLAY;
		}
		return this.innerFindImage(imageDir, imageType);
	}
	
	@Override
	public void checkAndResizeImage(String name) throws IOException {
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
		    String targetPath = importFile.getParentFile().getAbsolutePath() + File.separator + "%s." + ext;
		    
		    this.resizeImage(image, imagesWidth, imagesHeight, ext, String.format(targetPath, ImageTypes.DISPLAY.toString()));		    
		    this.resizeImage(image, thumbnailsWidth, thumbnailsHeight, ext, String.format(targetPath, ImageTypes.THUMBNAIL.toString()));		    
		} catch(IOException ex) {
			throw new IOException("The file could not be opened, an error occurred", ex);
		}
	}
	
	private File innerFindImage(File parent, ImageTypes imageType) {
		for (File f : parent.listFiles()) {
			if (f.getName().equals(imageType.toString()) || f.getName().startsWith(imageType.toString())) {
				return f;
			}
		}
		
		/* If the original has dimensions smaller or equal to the defined maxima for
		 *  our large and thumbnail images, no reduced-size images are necessary. 
		 *  The following code works the image query up to the next best size 
		 */
		if (imageType.equals(ImageTypes.THUMBNAIL)) {
			return this.innerFindImage(parent, ImageTypes.DISPLAY);
		}
		if (imageType.equals(ImageTypes.DISPLAY)) {
			return this.innerFindImage(parent, ImageTypes.ORIGINAL);
		}
		
		return null;
	}
	
	private void resizeImage(BufferedImage originalImage, int maxWidth, int maxHeight, String format, String path) throws IOException{
		if (format.toLowerCase().equals("jpeg")) {
			format = "jpg";
		}
		
		int imageType = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		int height;
		int width;
		
		if ((double)originalImage.getHeight() <= maxHeight && (double)originalImage.getWidth() <= maxWidth) {
			return;
		}
		
		if ( ((double)maxHeight / (double)originalImage.getHeight() ) <
			  ((double)maxWidth / (double)originalImage.getWidth() ) ) {
			height = maxHeight;
			width = (int)Math.floor((double)maxHeight / (double)originalImage.getHeight() * originalImage.getWidth());
		} else {
			height = (int)Math.floor((double)maxWidth / (double)originalImage.getWidth() * originalImage.getHeight());
			width = maxWidth;
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
}