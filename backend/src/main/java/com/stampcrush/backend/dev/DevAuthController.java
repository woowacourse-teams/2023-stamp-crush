package com.stampcrush.backend.dev;

import com.stampcrush.backend.auth.api.response.AuthTokensResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Profile({"local", "dev"})
@RequestMapping("/api/dev")
public class DevAuthController {

    private final DevAuthService devAuthService;

    @GetMapping("/register/admin/{joinedName}")
    public ResponseEntity<AuthTokensResponse> managerSignUp(
            @PathVariable String joinedName
    ) {
        return ResponseEntity.ok().body(devAuthService.joinManager(joinedName));
    }

    @GetMapping("/register/visitor/{joinedName}")
    public ResponseEntity<AuthTokensResponse> visitorSignUp(
            @PathVariable String joinedName
    ) {
        return ResponseEntity.ok().body(devAuthService.joinVisitor(joinedName));
    }

    @GetMapping("/login/admin/{joinedName}")
    public ResponseEntity<AuthTokensResponse> managerLogin(
            @PathVariable String joinedName
    ) {
        return ResponseEntity.ok(devAuthService.loginManger(joinedName));
    }

    @GetMapping("/login/visitor/{joinedName}")
    public ResponseEntity<AuthTokensResponse> visitorLogin(
            @PathVariable String joinedName
    ) {
        return ResponseEntity.ok(devAuthService.loginVisitor(joinedName));
    }
}
