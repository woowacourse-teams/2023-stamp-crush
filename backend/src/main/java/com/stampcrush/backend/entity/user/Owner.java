package com.stampcrush.backend.entity.user;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import com.stampcrush.backend.exception.OwnerUnAuthorizationException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
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

    public void checkPassword(String encryptedPassword) {
        if (!this.encryptedPassword.equals(encryptedPassword)) {
            throw new OwnerUnAuthorizationException("비밀번호가 맞지 않습니다");
        }
    }
}
