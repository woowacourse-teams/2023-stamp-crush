package com.stampcrush.backend.api.coupon.customer.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CouponResponse {

    private final Long id;
    private final String name;
    private final List<CouponInfoResponse> couponInfos;
}
