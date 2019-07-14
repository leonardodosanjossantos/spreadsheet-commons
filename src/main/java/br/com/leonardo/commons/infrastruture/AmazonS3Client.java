package br.com.leonardo.commons.infrastruture;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import br.com.leonardo.commons.model.FilePathData;

@Service
public class AmazonS3Client {
	
	private static final Logger log = LoggerFactory.getLogger(AmazonS3Client.class);


	@Value("${aws.region}")
	private String clientRegion;

	@Value("${aws.bucket}")
	private String bucketName;

	@Value("${aws.access.key}")
	private String accessKey;

	@Value("${aws.secret.key}")
	private String secretKey;

	private AmazonS3 getS3Client() {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion)
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

		return s3Client;
	}

	public FilePathData upload(File file) throws AmazonClientException, InterruptedException {
		AmazonS3 s3Client = getS3Client();
		try {
			TransferManager tm = TransferManagerBuilder.standard().withS3Client(getS3Client()).build();

			PutObjectRequest request = new PutObjectRequest(bucketName, file.getName(), file);
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType("application/excel");
			request.setMetadata(metadata);

			Upload upload = tm.upload(request);
			log.info("Object upload started");
			upload.waitForCompletion();
			log.info("Object upload complete");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			s3Client.shutdown();
		}
		FilePathData filePathData = new FilePathData(bucketName, file.getName());
		return filePathData;

	}

	public File download(FilePathData filePathData) throws IOException {

		S3Object fullObject = null;
		File file = null;
		try {

			log.info("Downloading an object");

			fullObject = getS3Client().getObject(filePathData.getPath(), filePathData.getName());

			final Path filePath = Paths.get(filePathData.getName());
			Files.copy(fullObject.getObjectContent(), filePath);
			file = filePath.toFile();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (fullObject != null) {
				fullObject.close();
			}
		}
		return file;
	}

}
