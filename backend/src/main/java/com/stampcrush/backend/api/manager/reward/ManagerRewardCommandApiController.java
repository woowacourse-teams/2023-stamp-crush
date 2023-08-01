package com.stampcrush.backend.api.manager.reward;

import com.stampcrush.backend.api.manager.reward.request.RewardUsedUpdateRequest;
import com.stampcrush.backend.application.manager.reward.RewardService;
import com.stampcrush.backend.application.manager.reward.dto.RewardUsedUpdateDto;
import com.stampcrush.backend.config.resolver.OwnerAuth;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/customers/{customerId}/rewards")
public class ManagerRewardCommandApiController {

    private final RewardService rewardService;

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
