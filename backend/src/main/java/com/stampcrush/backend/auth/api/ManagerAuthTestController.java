package com.stampcrush.backend.auth.api;

import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.ManagerAuthTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
