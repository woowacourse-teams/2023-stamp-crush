package com.stampcrush.backend.api.manager.coupon.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomerAccumulatingCouponsFindResponse {

    private List<CustomerAccumulatingCouponFindResponse> coupons;
}
