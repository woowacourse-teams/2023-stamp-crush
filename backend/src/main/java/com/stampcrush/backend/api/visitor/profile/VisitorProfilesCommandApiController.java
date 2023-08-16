package com.stampcrush.backend.api.visitor.profile;

import com.stampcrush.backend.api.visitor.profile.request.PhoneNumberUpdateRequest;
import com.stampcrush.backend.application.visitor.profile.VisitorProfilesCommandService;
import com.stampcrush.backend.config.resolver.CustomerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profiles")
public class VisitorProfilesCommandApiController {

    private final VisitorProfilesCommandService visitorProfilesCommandService;

    @PostMapping("/phone-number")
    public ResponseEntity<Void> savePhoneNumber(
            CustomerAuth customer,
            PhoneNumberUpdateRequest request
    ) {
        visitorProfilesCommandService.registerPhoneNumber(customer.getId(), request.getPhoneNumber());
        return ResponseEntity.ok().build();
    }
}
