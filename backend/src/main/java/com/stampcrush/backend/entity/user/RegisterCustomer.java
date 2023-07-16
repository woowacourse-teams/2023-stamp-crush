package com.stampcrush.backend.entity.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@DiscriminatorValue("register")
@Entity
public class RegisterCustomer extends Customer {

    private String loginId;
    private String encryptedPassword;

    public RegisterCustomer(String loginId, String encryptedPassword) {
        this.loginId = loginId;
        this.encryptedPassword = encryptedPassword;
    }

    protected RegisterCustomer() {
    }

    @Override
    public boolean isRegistered() {
        return true;
    }
}

