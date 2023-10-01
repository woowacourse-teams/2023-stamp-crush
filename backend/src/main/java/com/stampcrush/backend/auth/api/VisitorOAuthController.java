package com.stampcrush.backend.auth.api;

import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.util.KakaoLoginParams;
import com.stampcrush.backend.auth.application.visitor.VisitorAuthLoginService;
import com.stampcrush.backend.auth.application.visitor.VisitorOAuthService;
import com.stampcrush.backend.config.resolver.CustomerAuth;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@Profile("!test")
@RequestMapping("/api/login")
public class VisitorOAuthController {

    private final VisitorAuthLoginService visitorAuthLoginService;
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
        AuthTokensResponse tokensResponse = visitorAuthLoginService.login(params);

        addRefreshTokenCookieToResponse(response, tokensResponse.getRefreshToken());

        return ResponseEntity.ok(tokensResponse);
    }

    @GetMapping("/reissue-token")
    public ResponseEntity<AuthTokensResponse> reissueToken(CustomerAuth customer, @CookieValue(name = "refreshToken") String refreshToken, HttpServletResponse response) {
        Long customerId = customer.getId();
        AuthTokensResponse authTokensResponse = visitorAuthLoginService.reissueToken(customerId, refreshToken);

        addRefreshTokenCookieToResponse(response, authTokensResponse.getRefreshToken());

        return ResponseEntity.ok(authTokensResponse);
    }

    private void addRefreshTokenCookieToResponse(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(604800);
        response.addCookie(refreshTokenCookie);
    }
}
