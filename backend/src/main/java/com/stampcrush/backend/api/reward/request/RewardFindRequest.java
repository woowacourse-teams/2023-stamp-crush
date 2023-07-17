package com.stampcrush.backend.api.reward.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RewardFindRequest {

    private final Long cafeId;
    private final boolean used;
}
