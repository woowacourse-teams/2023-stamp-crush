package com.stampcrush.backend.api.cafe.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
public class CafeUpdateRequest {

    private LocalTime openTime;

    private LocalTime closeTime;

    private String telephoneNumber;

    private String cafeImageUrl;
}
