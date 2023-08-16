package com.stampcrush.backend.application.manager.cafe.dto;

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
}
