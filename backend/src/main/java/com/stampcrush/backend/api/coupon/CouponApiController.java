package com.stampcrush.backend.api.coupon;

import com.stampcrush.backend.api.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.coupon.response.*;
import com.stampcrush.backend.application.coupon.CouponService;
import com.stampcrush.backend.application.coupon.dto.CafeCustomerFindResultDto;
import com.stampcrush.backend.application.coupon.dto.CustomerAccumulatingCouponFindResultDto;
import com.stampcrush.backend.application.coupon.dto.StampCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CouponApiController {

    private final CouponService couponService;

    @GetMapping("/cafes/{cafeId}/customers")
    public ResponseEntity<CafeCustomersFindResponse> findCustomersByCafe(@PathVariable Long cafeId) {
        List<CafeCustomerFindResultDto> coupons = couponService.findCouponsByCafe(cafeId);
        List<CafeCustomerFindResponse> cafeCustomerFindResponses = coupons.stream()
                .map(CafeCustomerFindResponse::from)
                .toList();

        return ResponseEntity.ok(new CafeCustomersFindResponse(cafeCustomerFindResponses));
    }

    @GetMapping("/customers/{customerId}/coupons")
    public ResponseEntity<CustomerAccumulatingCouponsFindResponse> findCustomerUsingCouponByCafe(
            @PathVariable Long customerId,
            @RequestParam Long cafeId,
            @RequestParam boolean active
    ) {
        List<CustomerAccumulatingCouponFindResultDto> accumulatingCoupon = couponService.findAccumulatingCoupon(cafeId, customerId);

        List<CustomerAccumulatingCouponFindResponse> accumulatingResponses = accumulatingCoupon.stream()
                .map(CustomerAccumulatingCouponFindResponse::from)
                .toList();

        return ResponseEntity.ok(new CustomerAccumulatingCouponsFindResponse(accumulatingResponses));
    }

    @PostMapping("/customers/{customerId}/coupons")
    public ResponseEntity<CouponCreateResponse> createCoupon(
            @RequestBody @Valid CouponCreateRequest request,
            @PathVariable("customerId") Long customerId
    ) {
        Long couponId = couponService.createCoupon(request.getCafeId(), customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CouponCreateResponse(couponId));
    }

    @PostMapping("/customers/{customerId}/coupons/{couponId}/stamps/{ownerId}")
    public ResponseEntity<Void> createStamp(
            @PathVariable Long customerId,
            @PathVariable Long couponId,
            @PathVariable Long ownerId,
            @RequestBody @Valid StampCreateRequest request
    ) {
        StampCreateDto stampCreateDto = new StampCreateDto(ownerId, customerId, couponId, request.getEarningStampCount());
        couponService.createStamp(stampCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
