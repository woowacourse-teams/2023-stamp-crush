package com.stampcrush.backend.application.reward.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RewardFind {

    private final Long customerId;
    private final Long cafeId;
    private final boolean used;
}
