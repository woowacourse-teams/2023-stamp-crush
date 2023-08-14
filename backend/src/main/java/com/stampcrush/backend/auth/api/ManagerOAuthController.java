package com.stampcrush.backend.auth.api;

import com.stampcrush.backend.auth.application.ManagerOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/login")
public class ManagerOAuthController {

    private final ManagerOAuthService managerOAuthService;

    @GetMapping("/kakao")
    public ResponseEntity<Void> loginKakao() {
        System.out.println("여깃찌!");
        String redirectUri = managerOAuthService.findLoginRedirectUri();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUri))
                .build();
    }
}
