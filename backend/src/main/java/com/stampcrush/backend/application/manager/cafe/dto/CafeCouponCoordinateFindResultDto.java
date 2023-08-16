package com.stampcrush.backend.application.manager.cafe.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CafeCouponCoordinateFindResultDto {

    private final Integer order;
    private final Integer xCoordinate;
    private final Integer yCoordinate;
}
