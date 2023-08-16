package com.stampcrush.backend.api.manager.cafe.response;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CafeCouponCoordinateFindResponse {

    private final Integer order;
    private final Integer xCoordinate;
    private final Integer yCoordinate;

    public Integer getOrder() {
        return order;
    }

    public Integer getxCoordinate() {
        return xCoordinate;
    }

    public Integer getyCoordinate() {
        return yCoordinate;
    }
}
