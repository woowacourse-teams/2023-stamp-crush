package com.stampcrush.backend.auth.api;

import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.util.KakaoLoginParams;
import com.stampcrush.backend.auth.application.visitor.VisitorOAuthLoginService;
import com.stampcrush.backend.auth.application.visitor.VisitorOAuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/api/login")
public class VisitorOAuthController {

    private final VisitorOAuthLoginService visitorOAuthLoginService;
    private final VisitorOAuthService visitorOAuthService;

    @GetMapping("/kakao")
    public ResponseEntity<Void> loginKakao() {
        String redirectUri = visitorOAuthService.findLoginRedirectUri();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUri))
                .build();
    }

    @GetMapping("/kakao/token")
    public ResponseEntity<AuthTokensResponse> authorizeUser(@RequestParam("code") String authorizationCode, HttpServletResponse response) {
        KakaoLoginParams params = new KakaoLoginParams(authorizationCode);
        AuthTokensResponse tokensResponse = visitorOAuthLoginService.login(params);

        Cookie refreshToken = new Cookie("refreshToken", tokensResponse.getRefreshToken());
        refreshToken.setHttpOnly(true);
        response.addCookie(refreshToken);

        return ResponseEntity.ok(tokensResponse);
    }
}
