package org.example.bcm.core.service.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.example.bcm.core.service.S3Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class S3ServiceImpl implements S3Service {
    private final AmazonS3 amazonS3;
    //  @Value("${aws.access.awsAccessKey}")
    private String awsAccessKey = "AKIA6OLOSENVPS4SABVV";

    // @Value("${aws.access.awsSecretKey}")
    private String awsSecretKey = "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";

    // @Value("${aws.access.awsRegion}")
    private String awsRegion = "eu-west-3";

    // @Value("${aws.access.awsBucketName}")
    private String bucketName ="bcm-chkir";

    public S3ServiceImpl() {

        BasicAWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(awsRegion)
                .build();
    }
    @Override
    public String uploadFile( MultipartFile file) {
        try {
            long contentLength = file.getSize();
            String fileName =generateUniqueFileName(file.getOriginalFilename());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(contentLength);
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata ));
            return amazonS3.getUrl(bucketName, fileName).toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String generateUniqueFileName(String originalFileName) {
        String uniqueFileName = UUID.randomUUID().toString();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return uniqueFileName + fileExtension;
    }
}
