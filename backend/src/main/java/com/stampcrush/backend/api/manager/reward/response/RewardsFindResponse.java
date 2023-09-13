package com.stampcrush.backend.api.manager.reward.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RewardsFindResponse {

    private List<RewardFindResponse> rewards;
}
