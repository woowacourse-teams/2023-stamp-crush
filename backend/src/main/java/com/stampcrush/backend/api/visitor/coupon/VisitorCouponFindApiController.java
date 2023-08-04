package com.stampcrush.backend.api.visitor.coupon;

import com.stampcrush.backend.api.visitor.coupon.response.CustomerCouponsFindResponse;
import com.stampcrush.backend.application.visitor.coupon.VisitorCouponFindService;
import com.stampcrush.backend.application.visitor.coupon.dto.CustomerCouponFindResultDto;
import com.stampcrush.backend.config.resolver.CustomerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class VisitorCouponFindApiController {

    private final VisitorCouponFindService visitorCouponFindService;

    @GetMapping("/coupons")
    public ResponseEntity<CustomerCouponsFindResponse> findOneCouponForOneCafe(CustomerAuth customer) {
        List<CustomerCouponFindResultDto> coupons = visitorCouponFindService.findOneCouponForOneCafe(customer.getId());
        return ResponseEntity.ok()
                .body(CustomerCouponsFindResponse.from(coupons));
    }
}
