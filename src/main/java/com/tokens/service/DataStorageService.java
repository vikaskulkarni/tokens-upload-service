package com.tokens.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tokens.config.FileStorageProperties;
import com.tokens.exception.FileNotFoundException;
import com.tokens.exception.FileStorageException;
import com.tokens.model.TokensStore;
import com.tokens.repository.TokensFileRepo;

@Service
public class DataStorageService {

	private final Path fileStorageLocation;

	@Autowired
	private TokensFileRepo tokensFileRepo;

	private static final Logger logger = LoggerFactory.getLogger(DataStorageService.class);

	public Path getFileStorageLocation() {
		return fileStorageLocation;
	}

	@Autowired
	public DataStorageService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			validateFile(fileName);

			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Exception:", ex);
		}
	}

	private void validateFile(String fileName) {
		if (fileName.contains("..")) {
			throw new FileStorageException("Filename contains invalid path: " + fileName);
		}
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new FileNotFoundException("File not found " + fileName, ex);
		}
	}

	public TokensStore writeFileToDB(MultipartFile file, String externalFileId) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			validateFile(fileName);

			TokensStore tokensStore = new TokensStore(fileName, externalFileId, file.getContentType(), file.getBytes());

			return tokensFileRepo.save(tokensStore);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Exception:", ex);
		}
	}
}
