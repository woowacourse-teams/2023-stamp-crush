package com.stampcrush.backend.api.visitor.profile;

import com.stampcrush.backend.application.visitor.profile.VisitorProfilesCommandService;
import com.stampcrush.backend.config.resolver.CustomerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api")
public class VisitorCancelMembershipController {

    private final VisitorProfilesCommandService visitorProfilesCommandService;

    @DeleteMapping("/cancel-membership")
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
