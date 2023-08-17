package com.stampcrush.backend.entity.user;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.exception.CustomerUnAuthorizationException;
import jakarta.persistence.*;
import lombok.Builder;
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

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_provider")
    private OAuthProvider oAuthProvider;

    @Column(name = "oauth_id")
    private Long oAuthId;

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

    @Builder
    public RegisterCustomer(
            String nickname,
            String email,
            OAuthProvider oAuthProvider,
            Long oAuthId
    ) {
        super(nickname, null);
        this.email = email;
        this.oAuthProvider = oAuthProvider;
        this.oAuthId = oAuthId;
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

    public void registerLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void registerEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
