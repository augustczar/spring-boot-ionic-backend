package com.augustczar.cursomc.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.augustczar.cursomc.exceptions.FileRuntimeException;

@Service
public class S3Service {

	@Autowired
	private AmazonS3 s3Client;

	@Value("${s3.bucket}")
	private String bucketNme;

	private Logger LOG = org.slf4j.LoggerFactory.getLogger(S3Service.class);

	public URI uploadFile(MultipartFile multipartFile) {
		String fileName = null;
		String contentType = null;
		InputStream is = null;

		try {
			fileName = multipartFile.getOriginalFilename();
			is = multipartFile.getInputStream();
			contentType = multipartFile.getContentType();

			return uploadFile(is, fileName, contentType);

		} catch (IOException e) {
			throw new FileRuntimeException("Erro de IO: " + e.getMessage());
		}
	}

	private URI uploadFile(InputStream is, String fileName, String contentType) {

		ObjectMetadata objectMetadata = new ObjectMetadata();

		try {
			objectMetadata.setContentType(contentType);
			LOG.info("Iniciando upload!");
			s3Client.putObject(new PutObjectRequest(bucketNme, fileName, is, objectMetadata));
			LOG.info("Upload finlizado!");

			return s3Client.getUrl(bucketNme, fileName).toURI();

		} catch (URISyntaxException e) {
			throw new FileRuntimeException("Erro ao converter URL para URI!");
		}

	}
}
