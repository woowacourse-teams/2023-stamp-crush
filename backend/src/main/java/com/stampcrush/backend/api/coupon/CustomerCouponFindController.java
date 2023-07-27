package com.stampcrush.backend.api.coupon;

import com.stampcrush.backend.api.coupon.response.CustomerCouponsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CustomerCouponFindController {

    @GetMapping("/coupons/{customerId}") // customerId는 이후 제거 예정
    public CustomerCouponsResponse findCouponPerCafe(@PathVariable("customerId") Long customerId) {
        return null;
    }
}
