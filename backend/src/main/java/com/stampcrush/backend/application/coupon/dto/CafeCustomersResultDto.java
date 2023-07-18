package com.stampcrush.backend.application.coupon.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CafeCustomersResultDto {

    private final List<CafeCustomerInfoResultDto> customers;

    public CafeCustomersResultDto(List<CafeCustomerInfoResultDto> customers) {
        this.customers = customers;
    }
}
