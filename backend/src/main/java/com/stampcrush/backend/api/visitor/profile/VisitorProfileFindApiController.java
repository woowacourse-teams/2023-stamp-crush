package com.stampcrush.backend.api.visitor.profile;

import com.stampcrush.backend.api.visitor.profile.response.VisitorPhoneNumberFindResponse;
import com.stampcrush.backend.application.visitor.profile.VisitorProfileFindService;
import com.stampcrush.backend.application.visitor.profile.dto.VisitorPhoneNumberFindResultDto;
import com.stampcrush.backend.config.resolver.CustomerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class VisitorProfileFindApiController {

    private final VisitorProfileFindService visitorProfileFindService;

    @GetMapping("/phone-number")
    public ResponseEntity<VisitorPhoneNumberFindResponse> findPhoneNumber(CustomerAuth customer) {
        VisitorPhoneNumberFindResultDto dto = visitorProfileFindService.findPhoneNumber(customer.getId());
        return ResponseEntity.ok()
                .body(VisitorPhoneNumberFindResponse.from(dto));
    }
}
