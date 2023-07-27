package com.stampcrush.backend.api.coupon.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StampCreateRequest {

    @NotNull
    @PositiveOrZero
    private Integer earningStampCount;
}
