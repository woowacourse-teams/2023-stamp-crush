package com.stampcrush.backend.api.coupon;

import com.stampcrush.backend.api.coupon.response.CafeCustomerFindResponse;
import com.stampcrush.backend.api.coupon.response.CafeCustomersFindResponse;
import com.stampcrush.backend.api.coupon.response.CustomerAccumulatingCouponFindResponse;
import com.stampcrush.backend.api.coupon.response.CustomerAccumulatingCouponsFindResponse;
import com.stampcrush.backend.application.coupon.CouponService;
import com.stampcrush.backend.application.coupon.dto.CafeCustomersFindResultDto;
import com.stampcrush.backend.application.coupon.dto.CustomerAccumulatingCouponFindResultDto;
import lombok.RequiredArgsConstructor;
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
        CafeCustomersFindResultDto coupons = couponService.findCouponsByCafe(cafeId);
        List<CafeCustomerFindResponse> cafeCustomerFindResponses = coupons.getCustomers().stream()
                .map(CafeCustomerFindResponse::from)
                .toList();

        return ResponseEntity.ok(new CafeCustomersFindResponse(cafeCustomerFindResponses));
    }

    @GetMapping("/customers/{customerId}/coupons")
    public ResponseEntity<CustomerAccumulatingCouponsFindResponse> findCustomerUsingCouponByCafe(@PathVariable Long customerId, @RequestParam Long cafeId, @RequestParam boolean active) {
        List<CustomerAccumulatingCouponFindResultDto> accumulatingCoupon = couponService.findAccumulatingCoupon(cafeId, customerId);

        List<CustomerAccumulatingCouponFindResponse> accumulatingResponses = accumulatingCoupon.stream()
                .map(CustomerAccumulatingCouponFindResponse::from)
                .toList();


        return ResponseEntity.ok(new CustomerAccumulatingCouponsFindResponse(accumulatingResponses));
    }
}
