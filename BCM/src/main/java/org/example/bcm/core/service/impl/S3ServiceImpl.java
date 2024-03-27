package org.example.bcm.core.service.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.example.bcm.common.exception.FileUploadErrorException;
import org.example.bcm.common.exception.ResourceNotFoundException;
import org.example.bcm.core.service.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service {
    private final AmazonS3 amazonS3;
    private static final List<String> ALLOWED_IMAGE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");

    //    @Value("${aws.access.awsAccessKey}")
    private String awsAccessKey = "AKIAV2TMSDJHQK6DBIGX";

    //     @Value("${aws.access.awsSecretKey}")
    private String awsSecretKey = "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";

    //     @Value("${aws.access.awsRegion}")
    private String awsRegion = "us-east-1";

    //     @Value("${aws.access.awsBucketName}")
    private String bucketName = "movies-streaming";

    public S3ServiceImpl() {

        BasicAWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(awsRegion)
                .build();
    }

    private static String generateUniqueFileName(String originalFileName) {
        String uniqueFileName = UUID.randomUUID().toString();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return uniqueFileName + fileExtension;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            // Check if the file is empty
            if (file.isEmpty()) {
                throw new FileUploadErrorException("Cannot upload empty file");
            }

            // Check if the file is an image
            String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            if (!ALLOWED_IMAGE_EXTENSIONS.contains(fileExtension.toLowerCase())) {
                throw new FileUploadErrorException("Only image files (jpg, jpeg, png, gif) are allowed");
            }

            long contentLength = file.getSize();
            String fileName = generateUniqueFileName(file.getOriginalFilename());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(contentLength);
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
            return amazonS3.getUrl(bucketName, fileName).toString();
        } catch (IOException e) {
            throw new FileUploadErrorException("Error occurred while uploading file: " + e.getMessage());
        }
    }
}
