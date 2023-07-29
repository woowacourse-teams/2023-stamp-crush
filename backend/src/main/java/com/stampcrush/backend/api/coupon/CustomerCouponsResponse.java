package com.stampcrush.backend.api.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomerCouponsResponse {

    private final List<CustomerCouponResponse> coupons;
}
