package com.stampcrush.backend.api.visitor.visithistory.response;

import com.stampcrush.backend.application.visitor.visithistory.dto.CustomerStampHistoryFindResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class CustomerStampHistoryFindResponse {

    private Long id;
    private String cafeName;
    private int stampCount;
    private String createdAt;

    public static CustomerStampHistoryFindResponse from(CustomerStampHistoryFindResultDto serviceDto) {
        return new CustomerStampHistoryFindResponse(
                serviceDto.getId(),
                serviceDto.getCafeName(),
                serviceDto.getStampCount(),
                serviceDto.getCreatedAt()
        );
    }
}
