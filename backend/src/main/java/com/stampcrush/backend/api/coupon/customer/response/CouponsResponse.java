package com.stampcrush.backend.api.coupon.customer.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CouponsResponse {

    private final List<CouponResponse> coupons;
}
