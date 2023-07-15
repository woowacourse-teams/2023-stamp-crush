package com.stampcrush.backend.api.reward;

import com.stampcrush.backend.api.reward.dto.request.RewardUsedUpdateRequest;
import com.stampcrush.backend.application.reward.RewardService;
import com.stampcrush.backend.application.reward.dto.RewardUsedUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RewardApiController {

    private final RewardService rewardService;

    @PatchMapping("/customers/{customerId}/rewards/{rewardId}")
    public ResponseEntity<Void> updateRewardUsed(@PathVariable("customerId") Long customerId,
                                                 @PathVariable("rewardId") Long rewardId,
                                                 @RequestBody RewardUsedUpdateRequest rewardUsedUpdateRequest) {
        RewardUsedUpdate rewardUsedUpdate = new RewardUsedUpdate(rewardId, customerId, rewardUsedUpdateRequest.getCafeId(), rewardUsedUpdateRequest.isUsed());
        rewardService.updateUsed(rewardUsedUpdate);
        return ResponseEntity.ok().build();
    }
}
