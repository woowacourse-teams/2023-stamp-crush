package com.stampcrush.backend.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SampleFrontImageDto {

    private final Long id;
    private final String imageUrl;
}
