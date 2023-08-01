package com.stampcrush.backend.api.admin.cafe.response;

import com.stampcrush.backend.application.cafe.dto.CafeFindResultDto;
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

    public static CafeFindResponse from(CafeFindResultDto cafeFindResultDto) {
        return new CafeFindResponse(
                cafeFindResultDto.getId(),
                cafeFindResultDto.getName(),
                cafeFindResultDto.getOpenTime(),
                cafeFindResultDto.getCloseTime(),
                cafeFindResultDto.getTelephoneNumber(),
                cafeFindResultDto.getCafeImageUrl(),
                cafeFindResultDto.getRoadAddress(),
                cafeFindResultDto.getDetailAddress(),
                cafeFindResultDto.getBusinessRegistrationNumber()
        );
    }
}
