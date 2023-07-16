package com.stampcrush.backend.api.reward;

import com.stampcrush.backend.api.reward.dto.request.RewardFindRequest;
import com.stampcrush.backend.api.reward.dto.request.RewardUsedUpdateRequest;
import com.stampcrush.backend.api.reward.dto.response.RewardFindResponse;
import com.stampcrush.backend.api.reward.dto.response.RewardsFindResponse;
import com.stampcrush.backend.application.reward.RewardService;
import com.stampcrush.backend.application.reward.dto.RewardFind;
import com.stampcrush.backend.application.reward.dto.RewardFindResult;
import com.stampcrush.backend.application.reward.dto.RewardUsedUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RewardApiController {

    private final RewardService rewardService;

    @GetMapping("/customers/{customerId}/rewards")
    public ResponseEntity<RewardsFindResponse> findRewards(@PathVariable("customerId") Long customerId,
                                                           @ModelAttribute RewardFindRequest rewardFindRequest) {
        System.out.println("here");
        RewardFind rewardFind = new RewardFind(customerId, rewardFindRequest.getCafeId(), rewardFindRequest.isUsed());
        List<RewardFindResult> rewardFindResults = rewardService.findRewards(rewardFind);
        List<RewardFindResponse> rewardFindResponses = rewardFindResults.stream()
                .map(RewardFindResponse::new)
                .toList();
        RewardsFindResponse response = new RewardsFindResponse(rewardFindResponses);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/customers/{customerId}/rewards/{rewardId}")
    public ResponseEntity<Void> updateRewardUsed(@PathVariable("customerId") Long customerId,
                                                 @PathVariable("rewardId") Long rewardId,
                                                 @RequestBody RewardUsedUpdateRequest rewardUsedUpdateRequest) {
        RewardUsedUpdate rewardUsedUpdate = new RewardUsedUpdate(rewardId, customerId, rewardUsedUpdateRequest.getCafeId(), rewardUsedUpdateRequest.isUsed());
        rewardService.updateUsed(rewardUsedUpdate);
        return ResponseEntity.ok().build();
    }
}
