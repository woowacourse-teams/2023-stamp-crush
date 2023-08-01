package com.stampcrush.backend.api.admin.coupon.response;

import lombok.Getter;

import java.util.List;

@Getter
public class CafeCustomersFindResponse {

    private final List<CafeCustomerFindResponse> customers;

    public CafeCustomersFindResponse(List<CafeCustomerFindResponse> customers) {
        this.customers = customers;
    }
}
