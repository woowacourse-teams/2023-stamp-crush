package com.stampcrush.backend.auth.api;

import static org.springframework.boot.web.server.Cookie.SameSite.NONE;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.ManagerAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/login")
public class ManagerAuthController {

    private final ManagerAuthService managerAuthService;

    @PostMapping("/register/test/token")
    public ResponseEntity<AuthTokensResponse> joinManager(
            @RequestBody OAuthRegisterOwnerCreateRequest request,
            HttpServletResponse response
    ) {
        AuthTokensResponse tokenResponse = managerAuthService.join(
                request.getNickname(),
                request.getoAuthProvider(),
                request.getoAuthId()
        );
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
