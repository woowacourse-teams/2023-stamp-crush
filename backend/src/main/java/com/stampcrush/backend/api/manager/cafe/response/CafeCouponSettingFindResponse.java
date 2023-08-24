package com.stampcrush.backend.api.manager.cafe.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CafeCouponSettingFindResponse {

    private final String frontImageUrl;
    private final String backImageUrl;
    private final String stampImageUrl;
    private final List<CafeCouponCoordinateFindResponse> coordinates;
}
