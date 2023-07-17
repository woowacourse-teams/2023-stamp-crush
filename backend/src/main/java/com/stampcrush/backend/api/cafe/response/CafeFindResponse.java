package com.stampcrush.backend.api.cafe.response;

import com.stampcrush.backend.application.cafe.dto.CafeFindResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class CafeFindResponse {

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

    public static CafeFindResponse from(CafeFindResult cafeFindResult) {
        return new CafeFindResponse(
                cafeFindResult.getId(),
                cafeFindResult.getName(),
                cafeFindResult.getOpenTime(),
                cafeFindResult.getCloseTime(),
                cafeFindResult.getTelephoneNumber(),
                cafeFindResult.getCafeImageUrl(),
                cafeFindResult.getRoadAddress(),
                cafeFindResult.getDetailAddress(),
                cafeFindResult.getBusinessRegistrationNumber()
        );
    }
}
