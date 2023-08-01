package com.stampcrush.backend.api.manager.cafe;

import com.stampcrush.backend.api.manager.cafe.request.CafeCouponSettingUpdateRequest;
import com.stampcrush.backend.application.manager.cafe.ManagerCafeCouponSettingCommandService;
import com.stampcrush.backend.application.manager.cafe.dto.CafeCouponSettingDto;
import com.stampcrush.backend.config.resolver.OwnerAuth;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/coupon-setting")
public class ManagerCafeCouponSettingCommandApiController {

    private final ManagerCafeCouponSettingCommandService managerCafeCouponSettingCommandService;

    @PostMapping
    public ResponseEntity<Void> updateCafeCouponSetting(
            OwnerAuth owner,
            @RequestParam("cafe-id") Long cafeId,
            @RequestBody @Valid CafeCouponSettingUpdateRequest request
    ) {
        CafeCouponSettingDto cafeCouponSettingDto = request.toCouponSettingDto();
        managerCafeCouponSettingCommandService.updateCafeCouponSetting(cafeId, cafeCouponSettingDto);
        return ResponseEntity.noContent().build();
    }
}
