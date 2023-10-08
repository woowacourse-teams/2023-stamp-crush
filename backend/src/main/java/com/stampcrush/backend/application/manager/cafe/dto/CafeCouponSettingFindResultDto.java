package com.stampcrush.backend.application.manager.cafe.dto;

import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
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

    public static CafeCouponSettingFindResultDto from(CafeCouponDesign cafeCouponDesign) {
        return new CafeCouponSettingFindResultDto(
                cafeCouponDesign.getFrontImageUrl(),
                cafeCouponDesign.getBackImageUrl(),
                cafeCouponDesign.getStampImageUrl(),
                cafeCouponDesign.getCafeStampCoordinates().stream()
                        .map(stamp -> new CafeCouponCoordinateFindResultDto(
                                stamp.getStampOrder(),
                                stamp.getXCoordinate(),
                                stamp.getYCoordinate())).toList()
        );
    }
}
