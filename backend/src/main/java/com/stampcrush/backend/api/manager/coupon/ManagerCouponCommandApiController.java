package com.stampcrush.backend.api.manager.coupon;

import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.manager.coupon.response.CouponCreateResponse;
import com.stampcrush.backend.application.manager.coupon.ManagerCouponService;
import com.stampcrush.backend.application.manager.coupon.dto.StampCreateDto;
import com.stampcrush.backend.config.resolver.OwnerAuth;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class ManagerCouponCommandApiController {

    private final ManagerCouponService managerCouponService;

    @PostMapping("/customers/{customerId}/coupons")
    public ResponseEntity<CouponCreateResponse> createCoupon(
            OwnerAuth owner,
            @RequestBody @Valid CouponCreateRequest request,
            @PathVariable("customerId") Long customerId
    ) {
        Long couponId = managerCouponService.createCoupon(request.getCafeId(), customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CouponCreateResponse(couponId));
    }

    @PostMapping("/customers/{customerId}/coupons/{couponId}/stamps/{ownerId}")
    public ResponseEntity<Void> createStamp(
            OwnerAuth owner,
            @PathVariable Long customerId,
            @PathVariable Long couponId,
            @PathVariable Long ownerId,
            @RequestBody @Valid StampCreateRequest request
    ) {
        StampCreateDto stampCreateDto = new StampCreateDto(ownerId, customerId, couponId, request.getEarningStampCount());
        managerCouponService.createStamp(stampCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
