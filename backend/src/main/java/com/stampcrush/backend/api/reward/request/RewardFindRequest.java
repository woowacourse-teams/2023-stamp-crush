package com.stampcrush.backend.api.reward.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RewardFindRequest {

    private Long cafeId;
    private Boolean used;
}
