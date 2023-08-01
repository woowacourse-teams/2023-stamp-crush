package com.stampcrush.backend.api.manager.reward;

import com.stampcrush.backend.config.resolver.OwnerAuth;
import com.stampcrush.backend.api.manager.reward.request.RewardFindRequest;
import com.stampcrush.backend.api.manager.reward.request.RewardUsedUpdateRequest;
import com.stampcrush.backend.api.manager.reward.response.RewardFindResponse;
import com.stampcrush.backend.api.manager.reward.response.RewardsFindResponse;
import com.stampcrush.backend.application.reward.RewardService;
import com.stampcrush.backend.application.reward.dto.RewardFindDto;
import com.stampcrush.backend.application.reward.dto.RewardFindResultDto;
import com.stampcrush.backend.application.reward.dto.RewardUsedUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/customers/{customerId}/rewards")
public class ManagerRewardApiController {

    private final RewardService rewardService;

    @GetMapping
    public ResponseEntity<RewardsFindResponse> findRewards(
            OwnerAuth owner,
            @PathVariable("customerId") Long customerId,
            @ModelAttribute @Valid RewardFindRequest request
    ) {
        RewardFindDto rewardFindDto = new RewardFindDto(customerId, request.getCafeId(), request.getUsed());
        List<RewardFindResultDto> rewardFindResultDtos = rewardService.findRewards(rewardFindDto);
        List<RewardFindResponse> rewardFindResponses = rewardFindResultDtos.stream()
                .map(RewardFindResponse::new)
                .toList();
        RewardsFindResponse response = new RewardsFindResponse(rewardFindResponses);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{rewardId}")
    public ResponseEntity<Void> updateRewardToUsed(
            OwnerAuth owner,
            @PathVariable("customerId") Long customerId,
            @PathVariable("rewardId") Long rewardId,
            @RequestBody @Valid RewardUsedUpdateRequest request
    ) {
        RewardUsedUpdateDto rewardUsedUpdateDto = new RewardUsedUpdateDto(rewardId, customerId, request.getCafeId(), request.getUsed());
        rewardService.useReward(rewardUsedUpdateDto);
        return ResponseEntity.ok().build();
    }
}
