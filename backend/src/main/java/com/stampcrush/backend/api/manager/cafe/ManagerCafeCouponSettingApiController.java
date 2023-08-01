package com.stampcrush.backend.api.manager.cafe;

import com.stampcrush.backend.config.resolver.OwnerAuth;
import com.stampcrush.backend.api.manager.cafe.request.CafeCouponSettingUpdateRequest;
import com.stampcrush.backend.application.cafe.CafeCouponSettingService;
import com.stampcrush.backend.application.cafe.dto.CafeCouponSettingDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/coupon-setting")
public class ManagerCafeCouponSettingApiController {

    private final CafeCouponSettingService cafeCouponSettingService;

    @PostMapping
    public ResponseEntity<Void> updateCafeCouponSetting(OwnerAuth owner,
                                                        @RequestParam("cafe-id") Long cafeId,
                                                        @RequestBody @Valid CafeCouponSettingUpdateRequest request
    ) {
        CafeCouponSettingDto cafeCouponSettingDto = request.toCouponSettingDto();
        cafeCouponSettingService.updateCafeCouponSetting(cafeId, cafeCouponSettingDto);
        return ResponseEntity.noContent().build();
    }
}
