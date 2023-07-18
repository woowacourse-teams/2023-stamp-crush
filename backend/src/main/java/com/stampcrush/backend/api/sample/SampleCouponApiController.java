package com.stampcrush.backend.api.sample;

import com.stampcrush.backend.api.sample.request.SampleCouponFindRequest;
import com.stampcrush.backend.api.sample.response.SampleCouponFindResponse;
import com.stampcrush.backend.application.sample.SampleCouponService;
import com.stampcrush.backend.application.sample.dto.SampleCouponsFindResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/coupon-samples")
public class SampleCouponApiController {

    private final SampleCouponService sampleCouponService;

    @GetMapping
    public SampleCouponFindResponse findSampleCoupons(
            @ModelAttribute SampleCouponFindRequest request
    ) {
        SampleCouponsFindResultDto sampleCoupons = sampleCouponService.findSampleCouponsByMaxStampCount(request.getMaxStampCount());
        return SampleCouponFindResponse.from(sampleCoupons);
    }
}
