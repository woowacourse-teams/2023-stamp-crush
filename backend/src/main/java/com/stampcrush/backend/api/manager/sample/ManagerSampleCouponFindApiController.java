package com.stampcrush.backend.api.manager.sample;

import com.stampcrush.backend.api.manager.sample.response.SampleCouponFindResponse;
import com.stampcrush.backend.application.manager.sample.ManagerSampleCouponFindService;
import com.stampcrush.backend.application.manager.sample.dto.SampleCouponsFindResultDto;
import com.stampcrush.backend.config.resolver.OwnerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/coupon-samples")
public class ManagerSampleCouponFindApiController {

    private final ManagerSampleCouponFindService managerSampleCouponFindService;

    @GetMapping
    public SampleCouponFindResponse findSampleCoupons(OwnerAuth owner, @RequestParam("max-stamp-count") Integer maxStampCount) {
        SampleCouponsFindResultDto sampleCoupons = managerSampleCouponFindService.findSampleCouponsByMaxStampCount(maxStampCount);
        return SampleCouponFindResponse.from(sampleCoupons);
    }
}
