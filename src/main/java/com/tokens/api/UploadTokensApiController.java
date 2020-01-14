package com.tokens.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokens.model.EventToken;
import com.tokens.model.STATUS;
import com.tokens.model.TokensStore;
import com.tokens.model.UploadFileResponse;
import com.tokens.service.DataStorageService;
import com.tokens.service.TokensUploadService;

import io.swagger.annotations.ApiParam;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-18T15:17:56.845-07:00")
@RestController
public class UploadTokensApiController implements UploadTokensApi {

	private static final Logger logger = LoggerFactory.getLogger(UploadTokensApiController.class);

	private final TokensUploadService tokensUploadService;

	@Autowired
	private DataStorageService dataStorageService;

	@org.springframework.beans.factory.annotation.Autowired
	public UploadTokensApiController(ObjectMapper objectMapper, HttpServletRequest request,
			TokensUploadService tokensUploadService) {
		this.tokensUploadService = tokensUploadService;
	}

	@Override
	public UploadFileResponse uploadTokens(
			@ApiParam(value = "file detail") @Valid @RequestPart("file") MultipartFile tokenFile,
			@ApiParam(value = "Description of file contents.") @RequestParam(value = "note", required = false) String note) {
		String uniqueID = UUID.randomUUID().toString();
		String fileName = dataStorageService.storeFile(tokenFile);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();

		TokensStore tokensStore = dataStorageService.writeFileToDB(tokenFile, uniqueID);
		publishUploadEventWithStatus(tokensStore.getId(), uniqueID, STATUS.COMPLETED);

		return new UploadFileResponse(fileName, fileDownloadUri, tokenFile.getContentType(), tokenFile.getSize());
	}

	private void publishUploadEventWithStatus(Long fileId, String uniqueID, STATUS status) {
		long currentTimeMillis = System.currentTimeMillis();
		EventToken token = fileId != null
				? EventToken.builder().fileId(fileId).externalFileId(uniqueID).status(status)
						.timestamp(currentTimeMillis).build()
				: EventToken.builder().externalFileId(uniqueID).status(status).timestamp(currentTimeMillis).build();
		tokensUploadService.sendFileUploadEvent(token);
	}

	@Override
	public URL uploadTokensAsync(
			@ApiParam(value = "file detail") @Valid @RequestPart("file") MultipartFile tokenFile,
			@ApiParam(value = "Description of file contents.") @RequestParam(value = "note", required = false) String note) throws MalformedURLException {

		String uniqueID = UUID.randomUUID().toString();
		/*
		 * String getTokensUri =
		 * ServletUriComponentsBuilder.fromCurrentContextPath().path("/uploadStatus/")
		 * .path(uniqueID).toUriString();
		 */
		URL getTokensUri = new URL("http://localhost:10003/" + uniqueID);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> {
			dataStorageService.storeFile(tokenFile);
			TokensStore tokensStore = dataStorageService.writeFileToDB(tokenFile, uniqueID);
			publishUploadEventWithStatus(tokensStore.getId(), uniqueID, STATUS.COMPLETED);
		});
		publishUploadEventWithStatus(null, uniqueID, STATUS.STARTED);

		return getTokensUri;
	}

	@Override
	public List<UploadFileResponse> uploadMultipleTokens(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> uploadTokens(file, "")).collect(Collectors.toList());
	}

	@Override
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		// Load file as Resource
		Resource resource = dataStorageService.loadFileAsResource(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
