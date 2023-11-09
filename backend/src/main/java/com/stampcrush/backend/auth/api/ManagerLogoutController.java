package com.stampcrush.backend.auth.api;

import com.stampcrush.backend.auth.application.ManagerLogoutService;
import com.stampcrush.backend.config.resolver.OwnerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class ManagerLogoutController {

    private final ManagerLogoutService managerLogoutService;

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            OwnerAuth owner,
            @RequestHeader("Refresh") String refreshToken
    ) {
        managerLogoutService.logout(owner.getId(), refreshToken);
        return ResponseEntity.noContent().build();
    }
}
