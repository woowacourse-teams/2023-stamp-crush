package com.stampcrush.backend.api.manager.coupon;

import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.manager.coupon.response.CouponCreateResponse;
import com.stampcrush.backend.application.manager.coupon.ManagerCouponCommandService;
import com.stampcrush.backend.application.manager.coupon.dto.StampCreateDto;
import com.stampcrush.backend.config.resolver.OwnerAuth;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class ManagerCouponCommandApiController {

    private final ManagerCouponCommandService managerCouponCommandService;

    @PostMapping("/customers/{customerId}/coupons")
    public ResponseEntity<CouponCreateResponse> createCoupon(
            OwnerAuth owner,
            @RequestBody @Valid CouponCreateRequest request,
            @PathVariable("customerId") Long customerId
    ) {
        Long couponId = managerCouponCommandService.createCoupon(owner.getId(), request.getCafeId(), customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CouponCreateResponse(couponId));
    }

    @PostMapping("/customers/{customerId}/coupons/{couponId}/stamps")
    public ResponseEntity<Void> createStamp(
            OwnerAuth owner,
            @PathVariable("customerId") Long customerId,
            @PathVariable("couponId") Long couponId,
            @RequestBody @Valid StampCreateRequest request
    ) {
        StampCreateDto stampCreateDto = new StampCreateDto(owner.getId(), customerId, couponId, request.getEarningStampCount());
        managerCouponCommandService.createStamp(stampCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
