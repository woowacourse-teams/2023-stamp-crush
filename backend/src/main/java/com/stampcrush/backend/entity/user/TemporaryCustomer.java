package com.stampcrush.backend.entity.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("temporary")
@Entity
public class TemporaryCustomer extends Customer {

    protected TemporaryCustomer() {
    }

    public TemporaryCustomer(String nickname, String phoneNumber) {
        super(nickname, phoneNumber);
    }

    @Override
    public boolean isRegistered() {
        return false;
    }
}
