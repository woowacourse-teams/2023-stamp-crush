package com.stampcrush.backend.api.manager.coupon.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class CafeCustomersFindResponse {

    private List<CafeCustomerFindResponse> customers;

    public CafeCustomersFindResponse(List<CafeCustomerFindResponse> customers) {
        this.customers = customers;
    }
}
