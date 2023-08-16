package com.stampcrush.backend.entity.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
public class S3ImageUploader implements ImageUploader {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSSSSS");
    private static final String EXTENSION_DELIMITER = ".";

    private final AmazonS3 s3;

    @Value("${s3.bucket}")
    private String bucket;
    @Value("${s3.base-url}")
    private String BASE_URL;

    @Override
    public String upload(MultipartFile image) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(image.getContentType());
        objectMetadata.setContentLength(image.getSize());
        String formattedFileName = formatFileName(image.getOriginalFilename());
        try {
            s3.putObject(
                    new PutObjectRequest(
                            bucket,
                            formattedFileName,
                            image.getInputStream(),
                            objectMetadata
                    )
            );
        } catch (IOException exception) {
            throw new IllegalArgumentException("image 저장 실패");
        }
        return BASE_URL + formattedFileName;
    }

    private String formatFileName(String originalFileName) {
        return FORMATTER.format(LocalDateTime.now()) + originalFileName.substring(originalFileName.lastIndexOf(EXTENSION_DELIMITER));
    }
}
