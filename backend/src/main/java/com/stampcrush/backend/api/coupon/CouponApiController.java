package com.stampcrush.backend.api.coupon;

import com.stampcrush.backend.application.coupon.CouponService;
import com.stampcrush.backend.application.coupon.dto.CafeCustomersResponseDto;
import com.stampcrush.backend.application.coupon.dto.CustomerUsingCouponResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CouponApiController {

    private final CouponService couponService;

    @GetMapping("/cafes/{cafeId}/customers")
    public ResponseEntity<CafeCustomersResponseDto> readCustomersByCafe(@PathVariable Long cafeId) {
        return ResponseEntity.ok(couponService.findCouponsByCafe(cafeId));
    }

    @GetMapping("/customers/{customerId}/coupons")
    public ResponseEntity<List<CustomerUsingCouponResponseDto>> readCustomerUsingCouponByCafe(@PathVariable Long customerId, @RequestParam Long cafeId, @RequestParam boolean active) {
        return ResponseEntity.ok(couponService.findUsingCoupon(cafeId, customerId));
    }
}
