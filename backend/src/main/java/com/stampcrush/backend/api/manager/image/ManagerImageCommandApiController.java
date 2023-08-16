package com.stampcrush.backend.api.manager.image;

import com.stampcrush.backend.api.manager.image.response.ImageUrlResponse;
import com.stampcrush.backend.application.manager.image.ManagerImageCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class ManagerImageCommandApiController {

    private final ManagerImageCommandService managerImageCommandService;

    @PostMapping("/images")
    public ResponseEntity<ImageUrlResponse> uploadImage(@RequestPart MultipartFile image) {
        String url = managerImageCommandService.uploadImageAndReturnUrl(image);
        return ResponseEntity.ok(new ImageUrlResponse(url));
    }
}
