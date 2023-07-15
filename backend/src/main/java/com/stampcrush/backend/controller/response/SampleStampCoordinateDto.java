package com.stampcrush.backend.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SampleStampCoordinateDto {

    private final Long id;
    private final Integer stampOrder;
    private final Integer xCoordinate;
    private final Integer yCoordinate;
}
