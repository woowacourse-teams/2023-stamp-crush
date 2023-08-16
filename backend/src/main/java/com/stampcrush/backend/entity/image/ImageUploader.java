package com.stampcrush.backend.entity.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {

    String upload(MultipartFile image);
}
