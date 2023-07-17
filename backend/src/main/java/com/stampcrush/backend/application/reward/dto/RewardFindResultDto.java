package com.stampcrush.backend.application.reward.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RewardFindResultDto {

    private final Long id;
    private final String name;
}
