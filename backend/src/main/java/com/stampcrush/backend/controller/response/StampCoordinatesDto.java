package com.stampcrush.backend.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StampCoordinatesDto {

    private final Integer order;
    private final Integer xCoordinate;
    private final Integer yCoordinate;
}
