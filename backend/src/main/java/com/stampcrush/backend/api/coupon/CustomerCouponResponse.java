package com.stampcrush.backend.api.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomerCouponResponse {

    private final Long id;
    private final String name;
    private final List<CustomerCouponInfoResponse> couponInfos;
}
