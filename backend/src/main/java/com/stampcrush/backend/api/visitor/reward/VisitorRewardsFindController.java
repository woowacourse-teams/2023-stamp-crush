package com.stampcrush.backend.api.visitor.reward;

import com.stampcrush.backend.api.visitor.reward.response.VisitorRewardsFindResponse;
import com.stampcrush.backend.application.visitor.reward.VisitorRewardsFindService;
import com.stampcrush.backend.application.visitor.reward.dto.VisitorRewardsFindResultDto;
import com.stampcrush.backend.config.resolver.CustomerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rewards")
public class VisitorRewardsFindController {

    private final VisitorRewardsFindService visitorRewardsFindService;

    @GetMapping
    public ResponseEntity<VisitorRewardsFindResponse> findRewards(
            CustomerAuth customer,
            @RequestParam("used") Boolean used
    ) {
        List<VisitorRewardsFindResultDto> result = visitorRewardsFindService.findRewards(customer.getId(), used);
        return ResponseEntity.ok().body(VisitorRewardsFindResponse.from(result));
    }
}
