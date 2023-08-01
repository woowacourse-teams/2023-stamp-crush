package com.stampcrush.backend.api.admin.reward.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RewardUsedUpdateRequest {

    @NotNull
    @Positive
    private Long cafeId;

    @AssertTrue
    private Boolean used;
}
