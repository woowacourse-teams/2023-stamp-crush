package com.stampcrush.backend.application.manager.reward.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RewardFindDto {

    private final Long customerId;
    private final Long cafeId;
    private final boolean used;
}
