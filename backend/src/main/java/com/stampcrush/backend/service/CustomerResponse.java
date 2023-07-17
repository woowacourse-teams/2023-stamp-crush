package com.stampcrush.backend.service;

import com.stampcrush.backend.entity.user.Customer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PUBLIC;

@EqualsAndHashCode
@NoArgsConstructor(access = PUBLIC)
@AllArgsConstructor
@Getter
public class CustomerResponse {

    private Long id;
    private String nickname;
    private String phoneNumber;

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getNickname(), customer.getPhoneNumber());
    }
}
