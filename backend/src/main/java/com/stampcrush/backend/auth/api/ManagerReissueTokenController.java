package com.stampcrush.backend.auth.api;

import static org.springframework.boot.web.server.Cookie.SameSite.NONE;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.ManagerReissueTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/auth")
public class ManagerReissueTokenController {

    private final ManagerReissueTokenService managerReissueTokenService;

    @GetMapping("/reissue-token")
    public ResponseEntity<AuthTokensResponse> reissueToken(
            @CookieValue(name = "REFRESH_TOKEN") String refreshToken,
            HttpServletResponse response
    ) {
        AuthTokensResponse tokenResponse = managerReissueTokenService.reissueToken(refreshToken);
        ResponseCookie cookie = ResponseCookie.from("REFRESH_TOKEN", tokenResponse.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/api/admin/auth/reissue-token")
                .sameSite(NONE.attributeValue())
                .build();
        response.addHeader(SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(tokenResponse);
    }
}
