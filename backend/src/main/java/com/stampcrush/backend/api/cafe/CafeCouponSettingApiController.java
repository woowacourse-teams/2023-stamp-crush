package com.stampcrush.backend.api.cafe;

import com.stampcrush.backend.api.cafe.request.CafeCouponSettingUpdateRequest;
import com.stampcrush.backend.application.cafe.CafeCouponSettingService;
import com.stampcrush.backend.application.cafe.dto.CafeCouponSettingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/coupon-setting")
public class CafeCouponSettingApiController {

    private final CafeCouponSettingService cafeCouponSettingService;

    @PostMapping
    public ResponseEntity<Void> updateCafeCouponSetting(
            @RequestParam("cafe-id") Long cafeId,
            @RequestBody CafeCouponSettingUpdateRequest request
    ) {
        CafeCouponSettingDto cafeCouponSettingDto = request.toCouponSettingDto();
        cafeCouponSettingService.updateCafeCouponSetting(cafeId, cafeCouponSettingDto);
        return ResponseEntity.noContent().build();
    }
}
