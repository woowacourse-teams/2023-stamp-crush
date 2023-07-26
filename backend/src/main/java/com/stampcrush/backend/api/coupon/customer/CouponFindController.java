package com.stampcrush.backend.api.coupon.customer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CouponFindController {

    @GetMapping("/coupons/{customerId}") // customerId는 이후 제거 예정
    public void findCoupons(@PathVariable("customerId") Long customerId) {
    }
}
