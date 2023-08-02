package com.stampcrush.backend.api.manager.cafe.request;

import com.stampcrush.backend.application.manager.cafe.dto.CafeUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
public class CafeUpdateRequest {

    private String introduction;

    private LocalTime openTime;

    private LocalTime closeTime;

    private String telephoneNumber;

    private String cafeImageUrl;

    public CafeUpdateDto toServiceDto() {
        return new CafeUpdateDto(
                this.introduction,
                this.openTime,
                this.closeTime,
                this.telephoneNumber,
                this.cafeImageUrl
        );
    }
}
