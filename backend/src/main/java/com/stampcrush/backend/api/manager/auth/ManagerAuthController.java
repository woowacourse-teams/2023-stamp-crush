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
@RequestMapping("/api/admin")
public class ManagerAuthController {

    private final ManagerAuthService managerAuthService;

    @GetMapping("/login/naver")
    public ResponseEntity<Void> naverLogin() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(managerAuthService.createLoginRedirectUri()))
                .build();
    }
}
