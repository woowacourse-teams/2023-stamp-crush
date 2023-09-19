package com.stampcrush.backend.api.visitor.coupon.response;

import com.stampcrush.backend.application.visitor.coupon.dto.CustomerCouponFindResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCouponsFindResponse {

    private List<CustomerCouponFindResponse> coupons;

    public static CustomerCouponsFindResponse from(List<CustomerCouponFindResultDto> customerCouponFindResultDtos) {
        List<CustomerCouponFindResponse> coupons = customerCouponFindResultDtos.stream()
                .map(CustomerCouponFindResponse::from)
                .toList();
        return new CustomerCouponsFindResponse(coupons);
    }
}
