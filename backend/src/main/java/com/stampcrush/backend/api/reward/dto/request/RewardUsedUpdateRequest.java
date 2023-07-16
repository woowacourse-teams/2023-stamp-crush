package com.stampcrush.backend.api.reward.dto.request;

import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RewardUsedUpdateRequest {

    private final Long cafeId;

    @AssertTrue
    private final boolean used;
}
