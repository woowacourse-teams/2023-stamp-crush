package com.stampcrush.backend.api.visitor.coupon;

import com.stampcrush.backend.api.visitor.coupon.response.CustomerCouponsFindResponse;
import com.stampcrush.backend.application.visitor.coupon.VisitorCustomerCouponFindService;
import com.stampcrush.backend.application.visitor.coupon.dto.CustomerCouponFindResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class VisitorCustomerCouponFindApiController {

    private final VisitorCustomerCouponFindService visitorCustomerCouponFindService;

    @GetMapping("/coupons/{customerId}") // customerId는 이후 제거 예정
    public ResponseEntity<CustomerCouponsFindResponse> findOneCouponForOneCafe(@PathVariable("customerId") Long customerId) {
        List<CustomerCouponFindResultDto> coupons = visitorCustomerCouponFindService.findOneCouponForOneCafe(customerId);
        return ResponseEntity.ok().body(CustomerCouponsFindResponse.from(coupons));
    }
}
