package com.stampcrush.backend.api.customer.response;

import com.stampcrush.backend.application.customer.dto.CustomerFindDto;
import com.stampcrush.backend.application.customer.dto.CustomersFindResultDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class CustomersFindResponse {

    private List<CustomerFindDto> customer;

    public static CustomersFindResponse from(CustomersFindResultDto customersFindResultDto) {
        return new CustomersFindResponse(customersFindResultDto.getCustomer());
    }
}
