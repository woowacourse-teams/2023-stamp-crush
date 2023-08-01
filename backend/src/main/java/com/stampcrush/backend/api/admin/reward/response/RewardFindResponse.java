package com.stampcrush.backend.api.admin.reward.response;

import com.stampcrush.backend.application.reward.dto.RewardFindResultDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RewardFindResponse {

    private final Long id;
    private final String name;

    public RewardFindResponse(RewardFindResultDto rewardFindResultDto) {
        this.id = rewardFindResultDto.getId();
        this.name = rewardFindResultDto.getName();
    }
}
