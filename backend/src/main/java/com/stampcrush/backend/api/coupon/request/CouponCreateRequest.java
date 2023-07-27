package com.stampcrush.backend.api.coupon.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CouponCreateRequest {

    @NotNull
    @Positive
    private Long cafeId;
}
