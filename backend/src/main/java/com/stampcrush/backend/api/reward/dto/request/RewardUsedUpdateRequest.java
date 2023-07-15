package com.stampcrush.backend.api.reward.dto.request;

import lombok.Getter;

@Getter
public class RewardUsedUpdateRequest {

    private Long cafeId;
    private boolean used;

    public RewardUsedUpdateRequest(Long cafeId, Boolean used) {
        this.cafeId = cafeId;
        this.used = used;
    }
}
