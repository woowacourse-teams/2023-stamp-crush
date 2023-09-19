package com.stampcrush.backend.auth.api;

import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import com.stampcrush.backend.auth.application.VisitorAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login")
public class VisitorAuthController {

    private final VisitorAuthService visitorAuthService;

    @Deprecated
    @PostMapping("/temporary/test")
    public ResponseEntity<Void> joinTemporaryCustomer(
            @RequestParam("phone-number") String phoneNumber
    ) {
        Long id = visitorAuthService.joinTemporaryCustomer(phoneNumber);
        return ResponseEntity.created(URI.create("/customers/" + id)).build();
    }

    @PostMapping("/register/test/token")
    public ResponseEntity<AuthTokensResponse> joinRegisterCustomer(
            @RequestBody OAuthRegisterCustomerCreateRequest request
    ) {
        return ResponseEntity.ok().body(
                visitorAuthService.join(
                        request.getNickname(),
                        request.getEmail(),
                        request.getoAuthProvider(),
                        request.getoAuthId()
                )
        );
    }
}
