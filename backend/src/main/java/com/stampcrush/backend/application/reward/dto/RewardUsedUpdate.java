package com.stampcrush.backend.application.reward.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RewardUsedUpdate {

    private final Long rewardId;
    private final Long customerId;
    private final Long cafeId;
    private final Boolean used;
}
