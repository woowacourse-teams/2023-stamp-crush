package com.stampcrush.backend.application.customer.dto;

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
public class CustomerFindDto {

    private Long id;
    private String nickname;
    private String phoneNumber;

    public static CustomerFindDto from(Customer customer) {
        return new CustomerFindDto(customer.getId(), customer.getNickname(), customer.getPhoneNumber());
    }
}
