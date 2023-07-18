package com.stampcrush.backend.api.customer.response;

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
public class CustomerFindResponse {

    private Long id;
    private String nickname;
    private String phoneNumber;

    public static CustomerFindResponse from(Customer customer) {
        return new CustomerFindResponse(customer.getId(), customer.getNickname(), customer.getPhoneNumber());
    }
}
