package com.stampcrush.backend.application.cafe.dto;

import com.stampcrush.backend.entity.cafe.Cafe;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class CafeFindResultDto {

    private final Long id;
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

    public static CafeFindResultDto from(Cafe cafe) {
        return new CafeFindResultDto(
                cafe.getId(),
                cafe.getName(),
                cafe.getOpenTime(),
                cafe.getCloseTime(),
                cafe.getTelephoneNumber(),
                cafe.getCafeImageUrl(),
                cafe.getRoadAddress(),
                cafe.getDetailAddress(),
                cafe.getBusinessRegistrationNumber());
    }
}
