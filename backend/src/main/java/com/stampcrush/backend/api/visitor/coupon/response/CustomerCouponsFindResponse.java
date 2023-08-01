package com.stampcrush.backend.api.visitor.coupon.response;

import com.stampcrush.backend.application.coupon.dto.CustomerCouponFindResultDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CustomerCouponsFindResponse {

    private final List<CustomerCouponFindResponse> coupons;

    public static CustomerCouponsFindResponse from(List<CustomerCouponFindResultDto> customerCouponFindResultDtos) {
        List<CustomerCouponFindResponse> coupons = customerCouponFindResultDtos.stream()
                .map(CustomerCouponFindResponse::from)
                .toList();
        return new CustomerCouponsFindResponse(coupons);
    }
}
