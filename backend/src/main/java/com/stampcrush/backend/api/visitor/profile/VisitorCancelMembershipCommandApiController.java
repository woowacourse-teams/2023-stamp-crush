package com.stampcrush.backend.api.visitor.profile;

import com.stampcrush.backend.application.visitor.profile.VisitorCancelMembershipCommandService;
import com.stampcrush.backend.config.resolver.CustomerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class VisitorCancelMembershipCommandApiController {

    private final VisitorCancelMembershipCommandService visitorProfilesCommandService;

    @DeleteMapping("/customers")
    public ResponseEntity<Void> cancelMembership(
            CustomerAuth customer
    ) {
        final Long customerId = customer.getId();
        visitorProfilesCommandService.cancelMembership(customerId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
