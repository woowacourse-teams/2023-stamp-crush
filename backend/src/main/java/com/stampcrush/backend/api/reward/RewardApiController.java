package com.stampcrush.backend.api.reward;

import com.stampcrush.backend.api.reward.request.RewardFindRequest;
import com.stampcrush.backend.api.reward.request.RewardUsedUpdateRequest;
import com.stampcrush.backend.api.reward.response.RewardFindResponse;
import com.stampcrush.backend.api.reward.response.RewardsFindResponse;
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
public class RewardApiController {

    private final RewardService rewardService;

    @GetMapping("/customers/{customerId}/rewards")
    public ResponseEntity<RewardsFindResponse> findRewards(@PathVariable("customerId") Long customerId,
                                                           @ModelAttribute RewardFindRequest rewardFindRequest) {
        RewardFindDto rewardFindDto = new RewardFindDto(customerId, rewardFindRequest.getCafeId(), rewardFindRequest.isUsed());
        List<RewardFindResultDto> rewardFindResultDtos = rewardService.findRewards(rewardFindDto);
        List<RewardFindResponse> rewardFindResponses = rewardFindResultDtos.stream()
                .map(RewardFindResponse::new)
                .toList();
        RewardsFindResponse response = new RewardsFindResponse(rewardFindResponses);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/customers/{customerId}/rewards/{rewardId}")
    public ResponseEntity<Void> updateRewardToUsed(@PathVariable("customerId") Long customerId,
                                                   @PathVariable("rewardId") Long rewardId,
                                                   @RequestBody @Valid RewardUsedUpdateRequest request) {
        RewardUsedUpdateDto rewardUsedUpdateDto = new RewardUsedUpdateDto(rewardId, customerId, request.getCafeId(), request.isUsed());
        rewardService.useReward(rewardUsedUpdateDto);
        return ResponseEntity.ok().build();
    }
}
