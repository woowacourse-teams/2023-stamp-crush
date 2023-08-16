package com.stampcrush.backend.auth.api;

import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.manager.ManagerOAuthLoginService;
import com.stampcrush.backend.auth.application.manager.ManagerOAuthService;
import com.stampcrush.backend.auth.application.util.KakaoLoginParams;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@Profile("!test")
@RequestMapping("/api/admin/login")
public class ManagerOAuthController {

    private final ManagerOAuthService managerOAuthService;
    private final ManagerOAuthLoginService managerOAuthLoginService;

    @GetMapping("/kakao")
    public ResponseEntity<Void> loginKakao() {
        String redirectUri = managerOAuthService.findLoginRedirectUri();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUri))
                .build();
    }

    @GetMapping("/auth/kakao")
    public ResponseEntity<AuthTokensResponse> authorizeUser(@RequestParam("code") String authorizationCode) {
        KakaoLoginParams params = new KakaoLoginParams(authorizationCode);
        return ResponseEntity.ok(managerOAuthLoginService.login(params));
    }
}
