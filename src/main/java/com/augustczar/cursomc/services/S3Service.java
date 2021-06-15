package com.augustczar.cursomc.services;

import java.io.File;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${s3.bucket}")
	private String bucketNme;
	
	private Logger LOG = org.slf4j.LoggerFactory.getLogger(S3Service.class);
	
	public void uploadFile(String localFilePath) {
		File file = null;
		
		try {
			file = new File(localFilePath);
			LOG.info("Iniciando upload!");
			s3Client.putObject(new PutObjectRequest(bucketNme, "Teste.png", file));
			LOG.info("Upload finlizado!");
			
		} catch (AmazonServiceException e) {
			LOG.info("AmazonServiceException " + e.getErrorMessage());
			LOG.info("Status code " + e.getErrorCode());
		
		} catch (AmazonClientException e) {
			LOG.info("AmazonClientException " + e.getMessage());
		}
	}
}
