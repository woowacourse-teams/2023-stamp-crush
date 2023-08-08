package com.stampcrush.backend.entity.user;

import com.stampcrush.backend.exception.CustomerUnAuthorizationException;
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

    public RegisterCustomer(Long id, String nickname, String phoneNumber, String loginId, String encryptedPassword) {
        super(id, nickname, phoneNumber);
        this.loginId = loginId;
        this.encryptedPassword = encryptedPassword;
    }

    @Override
    public boolean isRegistered() {
        return true;
    }

    public void checkPassword(String encryptedPassword) {
        if (!this.encryptedPassword.equals(encryptedPassword)) {
            throw new CustomerUnAuthorizationException("아이디와 패스워드를 다시 확인 후 로그인해주세요.");
        }
    }
}
