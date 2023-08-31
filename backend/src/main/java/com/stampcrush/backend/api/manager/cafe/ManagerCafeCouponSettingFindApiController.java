package com.stampcrush.backend.api.manager.cafe;

import com.stampcrush.backend.api.manager.cafe.response.CafeCouponCoordinateFindResponse;
import com.stampcrush.backend.api.manager.cafe.response.CafeCouponSettingFindResponse;
import com.stampcrush.backend.application.manager.cafe.ManagerCafeCouponSettingFindService;
import com.stampcrush.backend.application.manager.cafe.dto.CafeCouponSettingFindResultDto;
import com.stampcrush.backend.config.resolver.OwnerAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/coupon-setting")
public class ManagerCafeCouponSettingFindApiController {

    private final ManagerCafeCouponSettingFindService managerCafeCouponSettingFindService;

    @GetMapping("/{couponId}")
    public ResponseEntity<CafeCouponSettingFindResponse> findCouponSetting(
            OwnerAuth owner,
            @RequestParam("cafe-id") Long cafeId,
            @PathVariable("couponId") Long couponId
    ) {
        CafeCouponSettingFindResultDto cafeCouponSetting = managerCafeCouponSettingFindService.findCouponSetting(cafeId, couponId);
        CafeCouponSettingFindResponse response = new CafeCouponSettingFindResponse(
                cafeCouponSetting.getFrontImageUrl(),
                cafeCouponSetting.getBackImageUrl(),
                cafeCouponSetting.getStampImageUrl(),
                cafeCouponSetting.getCoordinates().stream()
                        .map(stamp -> new CafeCouponCoordinateFindResponse(
                                stamp.getOrder(),
                                stamp.getXCoordinate(),
                                stamp.getYCoordinate())).toList());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<CafeCouponSettingFindResponse> findCafeCouponSetting(
            OwnerAuth owner,
            @RequestParam("cafe-id") Long cafeId
    ) {
        CafeCouponSettingFindResultDto cafeCouponSetting = managerCafeCouponSettingFindService.findCafeCouponSetting(cafeId);
        CafeCouponSettingFindResponse response = new CafeCouponSettingFindResponse(
                cafeCouponSetting.getFrontImageUrl(),
                cafeCouponSetting.getBackImageUrl(),
                cafeCouponSetting.getStampImageUrl(),
                cafeCouponSetting.getCoordinates().stream()
                        .map(stamp -> new CafeCouponCoordinateFindResponse(
                                stamp.getOrder(),
                                stamp.getXCoordinate(),
                                stamp.getYCoordinate())).toList());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
