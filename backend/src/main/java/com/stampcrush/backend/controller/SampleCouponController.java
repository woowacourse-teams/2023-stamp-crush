package com.stampcrush.backend.controller;

import com.stampcrush.backend.controller.request.SampleCouponQueryRequest;
import com.stampcrush.backend.controller.response.SampleCouponQueryResponse;
import com.stampcrush.backend.repository.sample.SampleFrontImageRepository;
import com.stampcrush.backend.service.SampleCouponService;
import com.stampcrush.backend.service.dto.SampleCouponsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/coupon-samples")
public class SampleCouponController {

    private final SampleCouponService sampleCouponService;
    private final SampleFrontImageRepository sampleFrontImageRepository;

    @GetMapping
    public SampleCouponQueryResponse findSampleCoupons(
            @ModelAttribute SampleCouponQueryRequest request
    ) {
        SampleCouponsDto sampleCoupons = sampleCouponService.findSampleCouponsBy(request.getMaxStampCount());
        return SampleCouponQueryResponse.from(sampleCoupons);
    }
}
