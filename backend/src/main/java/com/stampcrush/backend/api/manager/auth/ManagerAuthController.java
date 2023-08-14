package com.stampcrush.backend.api.manager.auth;

import com.stampcrush.backend.application.manager.auth.ManagerAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/login")
public class ManagerAuthController {

    private final ManagerAuthService managerAuthService;

    @GetMapping("/kakao")
    public ResponseEntity<Void> kakaoLogin() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(managerAuthService.createKakaoLoginRedirectUri()))
                .build();
    }

    @GetMapping("/naver")
    public ResponseEntity<Void> naverLogin() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(managerAuthService.createNaverLoginRedirectUri()))
                .build();
    }
}
