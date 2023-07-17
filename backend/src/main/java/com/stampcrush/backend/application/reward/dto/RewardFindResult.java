package com.stampcrush.backend.application.reward.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RewardFindResult {

    private final Long id;
    private final String name;
}
