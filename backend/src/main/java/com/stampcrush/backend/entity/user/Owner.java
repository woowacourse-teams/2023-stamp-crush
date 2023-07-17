package com.stampcrush.backend.entity.user;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class Owner extends BaseDate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String loginId;
    private String encryptedPassword;
    private String phoneNumber;

    public Owner(String name, String loginId, String encryptedPassword, String phoneNumber) {
        this.name = name;
        this.loginId = loginId;
        this.encryptedPassword = encryptedPassword;
        this.phoneNumber = phoneNumber;
    }

    protected Owner() {
    }
}
