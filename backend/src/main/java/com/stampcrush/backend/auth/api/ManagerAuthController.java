package com.stampcrush.backend.auth.api;

import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.ManagerAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login")
public class ManagerAuthController {

    private final ManagerAuthService managerAuthService;

    @PostMapping("/owner/register/test/token")
    public ResponseEntity<AuthTokensResponse> joinManager(
            @RequestBody OAuthRegisterOwnerCreateRequest request
    ) {
        return ResponseEntity.ok().body(
                managerAuthService.join(
                        request.getNickname(),
                        request.getoAuthProvider(),
                        request.getoAuthId()
                )
        );
    }
}
