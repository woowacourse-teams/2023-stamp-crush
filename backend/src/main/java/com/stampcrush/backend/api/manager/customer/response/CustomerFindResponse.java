package com.stampcrush.backend.api.manager.customer.response;

import com.stampcrush.backend.application.manager.customer.dto.CustomerFindDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class CustomerFindResponse {

    private Long id;
    private String nickname;
    private String phoneNumber;

    public static CustomerFindResponse from(CustomerFindDto customerFindDto) {
        return new CustomerFindResponse(customerFindDto.getId(), customerFindDto.getNickname(), customerFindDto.getPhoneNumber());
    }
}
