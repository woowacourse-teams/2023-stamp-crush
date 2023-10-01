package com.stampcrush.backend.auth.api;

import com.stampcrush.backend.auth.application.visitor.VisitorLogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/logout")
public class VisitorLogoutController {

    private final VisitorLogoutService visitorLogoutService;

    @GetMapping
    public ResponseEntity<Void> logout(@CookieValue(name = "refreshToken") String refreshToken
    ) {
        visitorLogoutService.logout(refreshToken);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/"))
                .build();
    }
}
