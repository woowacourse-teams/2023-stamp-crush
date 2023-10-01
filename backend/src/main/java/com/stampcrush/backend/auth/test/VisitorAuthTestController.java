package com.stampcrush.backend.auth.test;

import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("test")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login")
public class VisitorAuthTestController {

    private final VisitorAuthTestService visitorAuthTestService;

    @PostMapping("/register/test/token")
    public ResponseEntity<AuthTokensResponse> joinRegisterCustomer(
            @RequestBody OAuthRegisterCustomerCreateRequest request, HttpServletResponse response
    ) {
        AuthTokensResponse authTokensResponse = visitorAuthTestService.join(
                request.getNickname(),
                request.getEmail(),
                request.getoAuthProvider(),
                request.getoAuthId()
        );

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
