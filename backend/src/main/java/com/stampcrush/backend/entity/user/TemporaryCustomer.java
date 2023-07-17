package com.stampcrush.backend.entity.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@DiscriminatorValue("temporary")
@Entity
public class TemporaryCustomer extends Customer {

    public TemporaryCustomer(String nickname, String phoneNumber) {
        super(nickname, phoneNumber);
    }

    @Override
    public boolean isRegistered() {
        return false;
    }
}
