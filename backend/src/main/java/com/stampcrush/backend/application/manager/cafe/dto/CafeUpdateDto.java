package com.stampcrush.backend.application.manager.cafe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;


@Getter
@AllArgsConstructor
public class CafeUpdateDto {

    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final String telephoneNumber;
    private final String cafeImageUrl;
    // introduction이 현재 Cafe엔티티에 없음
}
