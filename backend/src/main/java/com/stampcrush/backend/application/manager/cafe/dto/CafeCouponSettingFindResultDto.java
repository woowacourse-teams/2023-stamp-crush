package com.stampcrush.backend.application.manager.cafe.dto;

import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.coupon.CouponDesign;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CafeCouponSettingFindResultDto {

    private final String frontImageUrl;
    private final String backImageUrl;
    private final String stampImageUrl;
    private final List<CafeCouponCoordinateFindResultDto> coordinates;

    public static CafeCouponSettingFindResultDto from(CafeCouponDesign couponDesign) {
        return new CafeCouponSettingFindResultDto(
                couponDesign.getFrontImageUrl(),
                couponDesign.getBackImageUrl(),
                couponDesign.getStampImageUrl(),
                couponDesign.getCafeStampCoordinates().stream()
                        .map(stamp -> new CafeCouponCoordinateFindResultDto(
                                stamp.getStampOrder(),
                                stamp.getXCoordinate(),
                                stamp.getYCoordinate())).toList()
        );
    }
}
