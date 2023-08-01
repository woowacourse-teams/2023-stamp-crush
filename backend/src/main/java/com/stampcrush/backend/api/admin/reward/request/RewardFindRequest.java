package com.stampcrush.backend.api.admin.reward.request;

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
