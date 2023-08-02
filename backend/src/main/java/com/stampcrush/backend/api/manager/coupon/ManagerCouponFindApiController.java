package com.stampcrush.backend.api.manager.coupon;

import com.stampcrush.backend.api.manager.coupon.response.CafeCustomerFindResponse;
import com.stampcrush.backend.api.manager.coupon.response.CafeCustomersFindResponse;
import com.stampcrush.backend.api.manager.coupon.response.CustomerAccumulatingCouponFindResponse;
import com.stampcrush.backend.api.manager.coupon.response.CustomerAccumulatingCouponsFindResponse;
import com.stampcrush.backend.application.manager.coupon.ManagerCouponFindService;
import com.stampcrush.backend.application.manager.coupon.dto.CafeCustomerFindResultDto;
import com.stampcrush.backend.application.manager.coupon.dto.CustomerAccumulatingCouponFindResultDto;
import com.stampcrush.backend.config.resolver.OwnerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class ManagerCouponFindApiController {

    private final ManagerCouponFindService managerCouponFindService;

    @GetMapping("/cafes/{cafeId}/customers")
    public ResponseEntity<CafeCustomersFindResponse> findCustomersByCafe(
            OwnerAuth owner,
            @PathVariable("cafeId") Long cafeId
    ) {
        List<CafeCustomerFindResultDto> coupons = managerCouponFindService.findCouponsByCafe(cafeId);
        List<CafeCustomerFindResponse> cafeCustomerFindResponses = coupons.stream()
                .map(CafeCustomerFindResponse::from)
                .toList();

        return ResponseEntity.ok(new CafeCustomersFindResponse(cafeCustomerFindResponses));
    }

    @GetMapping("/customers/{customerId}/coupons")
    public ResponseEntity<CustomerAccumulatingCouponsFindResponse> findCustomerUsingCouponByCafe(
            OwnerAuth owner,
            @PathVariable Long customerId,
            @RequestParam Long cafeId,
            @RequestParam boolean active
    ) {
        List<CustomerAccumulatingCouponFindResultDto> accumulatingCoupon = managerCouponFindService.findAccumulatingCoupon(cafeId, customerId);

        List<CustomerAccumulatingCouponFindResponse> accumulatingResponses = accumulatingCoupon.stream()
                .map(CustomerAccumulatingCouponFindResponse::from)
                .toList();

        return ResponseEntity.ok(new CustomerAccumulatingCouponsFindResponse(accumulatingResponses));
    }
}
