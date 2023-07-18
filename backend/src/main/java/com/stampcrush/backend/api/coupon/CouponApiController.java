package com.stampcrush.backend.api.coupon;

import com.stampcrush.backend.api.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.coupon.response.CouponCreateResponse;
import com.stampcrush.backend.application.coupon.CouponService;
import com.stampcrush.backend.application.coupon.dto.CafeCustomersResultDto;
import com.stampcrush.backend.application.coupon.dto.CustomerUsingCouponResultDto;
import com.stampcrush.backend.application.coupon.dto.StampCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CouponApiController {

    private final CouponService couponService;

    @GetMapping("/cafes/{cafeId}/customers")
    public ResponseEntity<CafeCustomersResultDto> findCustomersByCafe(@PathVariable Long cafeId) {
        return ResponseEntity.ok(couponService.findCouponsByCafe(cafeId));
    }

    @GetMapping("/customers/{customerId}/coupons")
    public ResponseEntity<List<CustomerUsingCouponResultDto>> findCustomerUsingCouponByCafe(@PathVariable Long customerId, @RequestParam Long cafeId, @RequestParam boolean active) {
        return ResponseEntity.ok(couponService.findUsingCoupon(cafeId, customerId));
    }

    @PostMapping("/customers/{customerId}/coupons")
    public ResponseEntity<CouponCreateResponse> createCoupon(@RequestBody CouponCreateRequest request, @PathVariable("customerId") Long customerId) {
        Long couponId = couponService.createCoupon(request.getCafeId(), customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CouponCreateResponse(couponId));
    }

    @PostMapping("/customers/{customerId}/coupons/{couponId}/stamps")
    public ResponseEntity<Void> createStamp(@PathVariable Long customerId, @PathVariable Long couponId, @RequestBody StampCreateRequest stampCreateRequest) {
        StampCreateDto stampCreateDto = new StampCreateDto(1L, customerId, couponId, stampCreateRequest.getEarningStampCount());
        couponService.createStamp(stampCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
