package com.stampcrush.backend.api.reward.dto.response;

import com.stampcrush.backend.application.reward.dto.RewardFindResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RewardFindResponse {

    private final Long id;
    private final String name;

    public RewardFindResponse(RewardFindResult rewardFindResult) {
        this.id = rewardFindResult.getId();
        this.name = rewardFindResult.getName();
    }
}
