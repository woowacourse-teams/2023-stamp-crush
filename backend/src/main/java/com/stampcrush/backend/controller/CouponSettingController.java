package com.stampcrush.backend.controller;

import com.stampcrush.backend.controller.request.CouponSettingModifyRequest;
import com.stampcrush.backend.service.CouponSettingService;
import com.stampcrush.backend.service.dto.CouponSettingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/coupon-setting")
public class CouponSettingController {

    private final CouponSettingService couponSettingService;

    @PostMapping
    public ResponseEntity<Void> modifyCouponSettings(
            @RequestParam("cafe-id") Long cafeId,
            @RequestBody CouponSettingModifyRequest request
    ) {
        CouponSettingDto couponSettingDto = request.toCouponSettingDto();
        couponSettingService.modifyCouponSettings(cafeId, couponSettingDto);
        return ResponseEntity.noContent().build();
    }
}
