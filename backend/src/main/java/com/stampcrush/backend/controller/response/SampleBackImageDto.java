package com.stampcrush.backend.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SampleBackImageDto {

    private final Long id;
    private final String imageUrl;
    private final List<SampleStampCoordinateDto> stampCoordinates;
}
