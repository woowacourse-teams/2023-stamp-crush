package com.stampcrush.backend.api.customer.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class CustomersFindResponse {

    private List<CustomerFindResponse> customer;

    public static CustomersFindResponse from(List<CustomerFindResponse> customerFindResponses) {
        return new CustomersFindResponse(customerFindResponses);
    }
}
