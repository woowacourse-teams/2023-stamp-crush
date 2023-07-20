package com.stampcrush.backend.application.coupon.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CafeCustomersFindResultDto {

    private final List<CafeCustomerFindResultDto> customers;

    public CafeCustomersFindResultDto(List<CafeCustomerFindResultDto> customers) {
        this.customers = customers;
    }
}
