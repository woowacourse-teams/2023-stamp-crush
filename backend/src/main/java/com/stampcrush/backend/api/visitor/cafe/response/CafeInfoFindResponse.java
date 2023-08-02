package com.stampcrush.backend.api.visitor.cafe.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stampcrush.backend.application.visitor.cafe.dto.CafeInfoFindByCustomerResultDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class CafeInfoFindResponse {

    private final Long id;
    private final String name;
    private final String introduction;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime openTime;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime closeTime;

    private final String telephoneNumber;
    private final String cafeImageUrl;
    private final String roadAddress;
    private final String detailAddress;

    public static CafeInfoFindResponse from(CafeInfoFindByCustomerResultDto cafeInfoFindByCustomerResultDto) {
        return new CafeInfoFindResponse(
                cafeInfoFindByCustomerResultDto.getId(),
                cafeInfoFindByCustomerResultDto.getName(),
                cafeInfoFindByCustomerResultDto.getIntroduction(),
                cafeInfoFindByCustomerResultDto.getOpenTime(),
                cafeInfoFindByCustomerResultDto.getCloseTime(),
                cafeInfoFindByCustomerResultDto.getTelephoneNumber(),
                cafeInfoFindByCustomerResultDto.getCafeImageUrl(),
                cafeInfoFindByCustomerResultDto.getRoadAddress(),
                cafeInfoFindByCustomerResultDto.getDetailAddress());
    }
}
