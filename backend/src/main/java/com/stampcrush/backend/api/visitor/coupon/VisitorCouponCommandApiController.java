package com.stampcrush.backend.api.visitor.coupon;

import com.stampcrush.backend.application.visitor.coupon.VisitorCouponCommandService;
import com.stampcrush.backend.config.resolver.CustomerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/coupons")
public class VisitorCouponCommandApiController {

    private final VisitorCouponCommandService visitorCouponCommandService;

    @DeleteMapping("/{couponId}")
    public ResponseEntity<Void> deleteCoupon(CustomerAuth customer, @PathVariable Long couponId) {
        visitorCouponCommandService.deleteCoupon(customer.getId(), couponId);
        return ResponseEntity.noContent().build();
    }
}
