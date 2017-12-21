package eu.dariah.de.colreg.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import de.unibamberg.minf.core.web.localization.MessageSource;
import de.unibamberg.minf.core.web.pojo.ModelActionPojo;
import eu.dariah.de.colreg.controller.base.BaseController;
import eu.dariah.de.colreg.service.ImageService;
import eu.dariah.de.colreg.service.ImageServiceImpl.ImageTypes;

@Controller
@RequestMapping("/image")
public class ImageController extends BaseController {
		
	@Autowired private MessageSource messageSource;
	@Autowired private ObjectMapper objectMapper;
	@Autowired private ImageService imageService;
	
	@RequestMapping(method = RequestMethod.GET, value = {"/{imageId}", "/{imageId}/{imageType}"})
	public @ResponseBody ResponseEntity<byte[]> getImage(@PathVariable String imageId, @PathVariable(required=false) ImageTypes imageType) {
		Assert.notNull(imageId);
		try {
			File f = imageService.findImage(imageId, imageType);
			if (f!=null && f.exists()) {
				InputStream fStream = new FileInputStream(f.getAbsolutePath());
				String type = URLConnection.guessContentTypeFromName(f.getName());

				byte[] out = IOUtils.toByteArray(fStream);

				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.add("content-disposition", "attachment; filename=" + f.getName());
				responseHeaders.add("Content-Type", type);

				return new ResponseEntity<byte[]>(out, responseHeaders, HttpStatus.OK);
			} 
		} catch (Exception e) {
			logger.error(String.format("Failed to load collection image [%s]", imageId), e);
		}
		return new ResponseEntity<byte[]>(new byte[0], HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/async/upload", produces = "application/json; charset=utf-8")
	public @ResponseBody ModelActionPojo uploadImage(@RequestParam("file") MultipartFile uploadfile, @RequestParam String collectionId, Locale locale) {
		ModelActionPojo result = new ModelActionPojo();
		
		try {
			String fileId = imageService.importImage(uploadfile);
			imageService.checkAndResizeImage(fileId);

			ObjectNode f = objectMapper.createObjectNode();
			f.put("name", fileId);
			
			ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentServletMapping();
			builder.path("/image/" + fileId + "/");
			String baseUri = builder.build().toUri().toString();
			f.put("baseUri", baseUri);
			f.put("imageUri", baseUri + ImageTypes.DISPLAY);
			f.put("thumbUri", baseUri + ImageTypes.THUMBNAIL);
			f.put("id", fileId);

			result.setSuccess(true);
			result.setPojo(f);
		} catch (Exception e) {
			logger.error("Failed to upload image", e);
			result.addObjectError(messageSource.getMessage("~eu.dariah.de.colreg.view.collection.labels.image_upload_failed", null, locale) + " \"" +e.getMessage() + "\"");	
		}
		return result;
	}

}
