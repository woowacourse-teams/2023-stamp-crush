package com.stampcrush.backend.application.cafe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;


@Getter
@AllArgsConstructor
public class CafeUpdateDto {

    private LocalTime openTime;
    private LocalTime closeTime;
    private String telephoneNumber;
    private String cafeImageUrl;
    // introduction이 현재 Cafe엔티티에 없음
}
