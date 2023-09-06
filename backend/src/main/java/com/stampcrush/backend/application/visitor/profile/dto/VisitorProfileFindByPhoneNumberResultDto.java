package com.stampcrush.backend.application.visitor.profile.dto;

import com.stampcrush.backend.entity.user.Customer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VisitorProfileFindByPhoneNumberResultDto {

    private final Long id;
    private final String nickname;
    private final String phoneNumber;
    private final String registerType;

    public static VisitorProfileFindByPhoneNumberResultDto from(Customer customer) {
        if (customer == null) {
            return null;
        }

        return new VisitorProfileFindByPhoneNumberResultDto(
                customer.getId(),
                customer.getNickname(),
                customer.getPhoneNumber(),
                getRegisterType(customer)
        );
    }

    private static String getRegisterType(Customer customer) {
        if (customer.isRegistered()) {
            return "register";
        }
        return "temporary";
    }
}
