package com.stampcrush.backend.application.manager.image;

import com.stampcrush.backend.entity.image.ImageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ManagerImageCommandService {

    private final ImageUploader imageUploader;

    public String uploadImageAndReturnUrl(MultipartFile image) {
        return imageUploader.upload(image);
    }
}
