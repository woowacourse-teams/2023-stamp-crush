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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        List<CafeCustomerFindResultDto> coupons = managerCouponFindService.findCouponsByCafe(owner.getId(), cafeId);
        List<CafeCustomerFindResponse> cafeCustomerFindResponses = coupons.stream()
                .map(CafeCustomerFindResponse::from)
                .toList();

        return ResponseEntity.ok(new CafeCustomersFindResponse(cafeCustomerFindResponses));
    }

    @GetMapping(value = "/cafes/{cafeId}/customers", params = "customer-type")
    public ResponseEntity<CafeCustomersFindResponse> findCustomersByCafeAndStatus(
            OwnerAuth owner,
            @PathVariable("cafeId") Long cafeId,
            @RequestParam("customer-type") String customerType
    ) {
        List<CafeCustomerFindResultDto> coupons = managerCouponFindService.findCouponsByCafeAndCustomerType(owner.getId(), cafeId, customerType);
        List<CafeCustomerFindResponse> cafeCustomerFindResponses = coupons.stream()
                .map(CafeCustomerFindResponse::from)
                .toList();

        return ResponseEntity.ok(new CafeCustomersFindResponse(cafeCustomerFindResponses));
    }

    @GetMapping("/customers/{customerId}/coupons")
    public ResponseEntity<CustomerAccumulatingCouponsFindResponse> findCustomerUsingCouponByCafe(
            OwnerAuth owner,
            @PathVariable("customerId") Long customerId,
            @RequestParam("cafe-id") Long cafeId,
            @RequestParam("active") boolean active
    ) {
        List<CustomerAccumulatingCouponFindResultDto> accumulatingCoupon = managerCouponFindService.findAccumulatingCoupon(owner.getId(), cafeId, customerId);

        List<CustomerAccumulatingCouponFindResponse> accumulatingResponses = accumulatingCoupon.stream()
                .map(CustomerAccumulatingCouponFindResponse::from)
                .toList();

        return ResponseEntity.ok(new CustomerAccumulatingCouponsFindResponse(accumulatingResponses));
    }
}
