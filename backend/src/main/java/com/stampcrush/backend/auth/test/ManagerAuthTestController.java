package com.stampcrush.backend.auth.test;

import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
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
@RequestMapping("/api/admin/login")
public class ManagerAuthTestController {

    private final ManagerAuthTestService managerAuthTestService;

    @PostMapping("/register/test/token")
    public ResponseEntity<AuthTokensResponse> joinManager(
            @RequestBody OAuthRegisterOwnerCreateRequest request
    ) {
        return ResponseEntity.ok().body(
                managerAuthTestService.join(
                        request.getNickname(),
                        request.getoAuthProvider(),
                        request.getoAuthId()
                )
        );
    }
}
