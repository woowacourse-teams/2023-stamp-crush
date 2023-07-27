package com.stampcrush.backend.api.sample;

import com.stampcrush.backend.api.sample.response.SampleCouponFindResponse;
import com.stampcrush.backend.application.sample.SampleCouponService;
import com.stampcrush.backend.application.sample.dto.SampleCouponsFindResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/coupon-samples")
public class SampleCouponApiController {

    private final SampleCouponService sampleCouponService;

    @GetMapping
    public SampleCouponFindResponse findSampleCoupons(@RequestParam("max-stamp-count") Integer maxStampCount) {
        SampleCouponsFindResultDto sampleCoupons = sampleCouponService.findSampleCouponsByMaxStampCount(maxStampCount);
        return SampleCouponFindResponse.from(sampleCoupons);
    }
}
