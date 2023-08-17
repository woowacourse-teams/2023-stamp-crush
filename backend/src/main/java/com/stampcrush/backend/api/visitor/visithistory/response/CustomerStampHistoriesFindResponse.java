package com.stampcrush.backend.api.visitor.visithistory.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
public class CustomerStampHistoriesFindResponse {

    private List<CustomerStampHistoryFindResponse> stampHistories;
}
