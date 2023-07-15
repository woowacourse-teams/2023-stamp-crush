package com.stampcrush.backend.application.reward.dto;

import lombok.Getter;

@Getter
public class RewardUsedUpdate {

    private Long rewardId;
    private Long customerId;
    private Long cafeId;
    private Boolean used;

    public RewardUsedUpdate(Long rewardId, Long customerId, Long cafeId, Boolean used) {
        this.rewardId = rewardId;
        this.customerId = customerId;
        this.cafeId = cafeId;
        this.used = used;
    }
}
