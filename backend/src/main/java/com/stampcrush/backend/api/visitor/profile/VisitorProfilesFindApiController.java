package com.stampcrush.backend.api.visitor.profile;

import com.stampcrush.backend.api.visitor.profile.response.VisitorProfilesFindResponse;
import com.stampcrush.backend.application.visitor.profile.VisitorProfilesFindService;
import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfileFindResultDto;
import com.stampcrush.backend.config.resolver.CustomerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profiles")
public class VisitorProfilesFindApiController {

    private final VisitorProfilesFindService visitorProfilesFindService;

    @GetMapping
    public ResponseEntity<VisitorProfilesFindResponse> findProfiles(CustomerAuth customer) {
        VisitorProfileFindResultDto dto = visitorProfilesFindService.findVisitorProfile(customer.getId());

        return ResponseEntity.ok().body(VisitorProfilesFindResponse.from(dto));
    }
}
