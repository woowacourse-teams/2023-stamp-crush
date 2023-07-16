package com.stampcrush.backend.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CouponSettingModifyRequest {

    private final String frontImageUrl;
    private final String backImageUrl;
    private final String stampImageUrl;
    private final List<CouponStampCoordinateRequest> coordinates;
    private final String reward;
    private final Integer expirePeriod;

    @Getter
    @RequiredArgsConstructor
    public static class CouponStampCoordinateRequest {

        private final Integer order;
        private final Integer xCoordinate;
        private final Integer yCoordinate;
    }
}
