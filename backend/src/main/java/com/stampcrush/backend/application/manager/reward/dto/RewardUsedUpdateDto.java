package com.stampcrush.backend.application.manager.reward.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RewardUsedUpdateDto {

    private final Long rewardId;
    private final Long customerId;
    private final Long cafeId;
    private final Boolean used;
}
