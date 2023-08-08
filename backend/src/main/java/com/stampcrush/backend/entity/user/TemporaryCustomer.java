package com.stampcrush.backend.entity.user;

import com.stampcrush.backend.exception.CustomerBadRequestException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@DiscriminatorValue("temporary")
@Entity
public class TemporaryCustomer extends Customer {

    private static final int NICKNAME_LENGTH = 4;

    private TemporaryCustomer(String nickname, String phoneNumber) {
        super(nickname, phoneNumber);
    }

    public TemporaryCustomer(Long id, String nickname, String phoneNumber) {
        super(id, nickname, phoneNumber);
    }

    public static TemporaryCustomer from(String phoneNumber) {
        return new TemporaryCustomer(formatNickname(phoneNumber), phoneNumber);
    }

    private static String formatNickname(String phoneNumber) {
        if (phoneNumber.length() < NICKNAME_LENGTH) {
            throw new CustomerBadRequestException("임시 닉네임을 사용하려면 4글자 이상의 전화번호가 필요합니다");
        }
        return phoneNumber.substring(phoneNumber.length() - NICKNAME_LENGTH);
    }

    @Override
    public boolean isRegistered() {
        return false;
    }
}
