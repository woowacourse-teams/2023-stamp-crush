package com.stampcrush.backend.application.coupon.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CafeCustomersResponseDto {

    private final List<CafeCustomerInfoResponseDto> customers;

    public CafeCustomersResponseDto(List<CafeCustomerInfoResponseDto> customers) {
        this.customers = customers;
    }
}
