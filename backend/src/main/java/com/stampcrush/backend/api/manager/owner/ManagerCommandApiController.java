package com.stampcrush.backend.api.manager.owner;

import com.stampcrush.backend.api.manager.owner.request.OwnerCreateRequest;
import com.stampcrush.backend.api.manager.owner.request.OwnerLoginRequest;
import com.stampcrush.backend.application.manager.owner.ManagerCommandService;
import com.stampcrush.backend.application.manager.owner.dto.OwnerCreateDto;
import com.stampcrush.backend.application.manager.owner.dto.OwnerLoginDto;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.config.resolver.OwnerAuth;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class ManagerCommandApiController {

    private final ManagerCommandService managerCommandService;

    @PostMapping("/owners")
    ResponseEntity<Void> register(@RequestBody OwnerCreateRequest ownerCreateRequest) {
        managerCommandService.register(OwnerCreateDto.from(ownerCreateRequest));
        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PostMapping("/login")
    ResponseEntity<AuthTokensResponse> login(@RequestBody OwnerLoginRequest ownerLoginRequest, HttpServletResponse response) {
        AuthTokensResponse token = managerCommandService.login(OwnerLoginDto.from(ownerLoginRequest));

        addRefreshTokenCookieToResponse(response, token.getRefreshToken());

        return ResponseEntity.ok(token);
    }

    @GetMapping("/login/reissue-token")
    public ResponseEntity<AuthTokensResponse> reissueToken(OwnerAuth owner, @CookieValue(name = "refreshToken") String refreshToken, HttpServletResponse response) {
        Long ownerId = owner.getId();
        AuthTokensResponse authTokensResponse = managerCommandService.reissueToken(ownerId, refreshToken);

        addRefreshTokenCookieToResponse(response, authTokensResponse.getRefreshToken());

        return ResponseEntity.ok(authTokensResponse);
    }

    private void addRefreshTokenCookieToResponse(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/admin");
        refreshTokenCookie.setMaxAge(604800);
        response.addCookie(refreshTokenCookie);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue(name = "refreshToken") String refreshToken
    ) {
        managerCommandService.logout(refreshToken);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/admin"))
                .build();
    }
}
