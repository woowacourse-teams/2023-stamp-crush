package com.stampcrush.backend.auth.api;

import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.util.KakaoLoginParams;
import com.stampcrush.backend.auth.application.ManagerOAuthLoginService;
import com.stampcrush.backend.auth.application.ManagerOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@Profile("!test")
public class ManagerOAuthController {

    private final ManagerOAuthService managerOAuthService;
    private final ManagerOAuthLoginService managerOAuthLoginService;

    @GetMapping("/api/admin/login/kakao")
    public ResponseEntity<Void> loginKakao() {
        String redirectUri = managerOAuthService.findLoginRedirectUri();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUri))
                .build();
    }

    @GetMapping("/admin/login/auth/kakao")
    public ResponseEntity<AuthTokensResponse> authorizeUser(@RequestParam("code") String code) {
        KakaoLoginParams params = new KakaoLoginParams(code);
        return ResponseEntity.ok(managerOAuthLoginService.login(params));
    }
}
