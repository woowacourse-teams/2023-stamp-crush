package com.stampcrush.backend.application.cafe.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class CafeCreate {

    private final Long ownerId;

    private final String name;

    @DateTimeFormat(pattern = "HH:mm")
    private final LocalTime openTime;

    @DateTimeFormat(pattern = "HH:mm")
    private final LocalTime closeTime;

    private final String telephoneNumber;

    private final String cafeImageUrl;

    private final String roadAddress;

    private final String detailAddress;

    private final String businessRegistrationNumber;
}
