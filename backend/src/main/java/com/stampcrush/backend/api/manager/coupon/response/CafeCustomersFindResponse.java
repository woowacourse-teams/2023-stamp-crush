package com.stampcrush.backend.api.manager.coupon.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CafeCustomersFindResponse {

    private List<CafeCustomerFindResponse> customers;
}
