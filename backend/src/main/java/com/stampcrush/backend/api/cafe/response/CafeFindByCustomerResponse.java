package com.stampcrush.backend.api.cafe.response;

import com.stampcrush.backend.application.cafe.dto.CafeFindByCustomerResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@RequiredArgsConstructor
public class CafeFindByCustomerResponse {

    private final Long id;
    private final String name;
    //  private final String introduction;

    @DateTimeFormat(pattern = "HH:mm")
    private final LocalTime openTime;

    @DateTimeFormat(pattern = "HH:mm")
    private final LocalTime closeTime;

    private final String telephoneNumber;
    private final String cafeImageUrl;
    private final String roadAddress;
    private final String detailAddress;

    public static CafeFindByCustomerResponse from(CafeFindByCustomerResultDto cafeFindByCustomerResultDto) {
        return new CafeFindByCustomerResponse(
                cafeFindByCustomerResultDto.getId(),
                cafeFindByCustomerResultDto.getName(),
                cafeFindByCustomerResultDto.getOpenTime(),
                cafeFindByCustomerResultDto.getCloseTime(),
                cafeFindByCustomerResultDto.getTelephoneNumber(),
                cafeFindByCustomerResultDto.getCafeImageUrl(),
                cafeFindByCustomerResultDto.getRoadAddress(),
                cafeFindByCustomerResultDto.getDetailAddress());
    }
}
