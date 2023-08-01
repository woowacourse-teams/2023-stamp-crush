package com.stampcrush.backend.api.admin.reward.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RewardsFindResponse {

    private final List<RewardFindResponse> rewards;
}
