package com.stampcrush.backend.application.manager.cafe.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CafeCreateDto {

    private final Long ownerId;

    private final String name;

    private final String roadAddress;

    private final String detailAddress;

    private final String businessRegistrationNumber;
}
