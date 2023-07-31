package com.stampcrush.backend.api.coupon;

import com.stampcrush.backend.api.coupon.response.CustomerCouponsFindResponse;
import com.stampcrush.backend.application.coupon.CustomerCouponFindService;
import com.stampcrush.backend.application.coupon.dto.CustomerCouponFindResultDto;
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
public class CustomerCouponFindController {

    private final CustomerCouponFindService customerCouponFindService;

    @GetMapping("/coupons/{customerId}") // customerId는 이후 제거 예정
    public ResponseEntity<CustomerCouponsFindResponse> findOneCouponForOneCafe(@PathVariable("customerId") Long customerId) {
        List<CustomerCouponFindResultDto> coupons = customerCouponFindService.findOneCouponForOneCafe(customerId);
        return ResponseEntity.ok().body(CustomerCouponsFindResponse.from(coupons));
    }
}
