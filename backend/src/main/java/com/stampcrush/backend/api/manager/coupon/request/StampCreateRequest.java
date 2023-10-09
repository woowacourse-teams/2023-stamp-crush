package com.stampcrush.backend.api.manager.coupon.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StampCreateRequest {

    @Max(value = 10)
    @NotNull
    @Positive
    private Integer earningStampCount;
}
