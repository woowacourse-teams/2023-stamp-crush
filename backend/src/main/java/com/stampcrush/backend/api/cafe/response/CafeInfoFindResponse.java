package com.stampcrush.backend.api.cafe.response;

import com.stampcrush.backend.application.cafe.dto.CafeInfoFindByCustomerResultDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class CafeInfoFindResponse {

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

    public static CafeInfoFindResponse from(CafeInfoFindByCustomerResultDto cafeInfoFindByCustomerResultDto) {
        return new CafeInfoFindResponse(
                cafeInfoFindByCustomerResultDto.getId(),
                cafeInfoFindByCustomerResultDto.getName(),
                cafeInfoFindByCustomerResultDto.getOpenTime(),
                cafeInfoFindByCustomerResultDto.getCloseTime(),
                cafeInfoFindByCustomerResultDto.getTelephoneNumber(),
                cafeInfoFindByCustomerResultDto.getCafeImageUrl(),
                cafeInfoFindByCustomerResultDto.getRoadAddress(),
                cafeInfoFindByCustomerResultDto.getDetailAddress());
    }
}
