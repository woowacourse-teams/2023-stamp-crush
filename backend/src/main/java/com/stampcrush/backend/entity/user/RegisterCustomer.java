package com.stampcrush.backend.entity.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@DiscriminatorValue("register")
@Entity
public class RegisterCustomer extends Customer {

    private String loginId;
    private String encryptedPassword;

    public RegisterCustomer(String nickname, String phoneNumber, String loginId, String encryptedPassword) {
        super(nickname, phoneNumber);
        this.loginId = loginId;
        this.encryptedPassword = encryptedPassword;
    }

    @Override
    public boolean isRegistered() {
        return true;
    }
}
