package com.stampcrush.backend.application.visitor.visithistory.dto;

import com.stampcrush.backend.entity.visithistory.VisitHistory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class CustomerStampHistoryFindResultDto {

    private final Long id;
    private final String cafeName;
    private final int stampCount;
    private final LocalDateTime createdAt;

    public static CustomerStampHistoryFindResultDto from(VisitHistory visitHistory) {
        return new CustomerStampHistoryFindResultDto(
                visitHistory.getId(),
                visitHistory.getCafeName(),
                visitHistory.getStampCount(),
                visitHistory.getCreatedAt()
        );
    }
}
