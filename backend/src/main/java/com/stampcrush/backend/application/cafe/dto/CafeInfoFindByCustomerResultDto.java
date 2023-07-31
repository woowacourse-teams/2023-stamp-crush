package com.stampcrush.backend.application.cafe.dto;

import com.stampcrush.backend.entity.cafe.Cafe;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class CafeInfoFindByCustomerResultDto {

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

    public static CafeInfoFindByCustomerResultDto from(Cafe cafe) {
        return new CafeInfoFindByCustomerResultDto(
                cafe.getId(),
                cafe.getName(),
                cafe.getOpenTime(),
                cafe.getCloseTime(),
                cafe.getTelephoneNumber(),
                cafe.getCafeImageUrl(),
                cafe.getRoadAddress(),
                cafe.getDetailAddress());
    }
}
