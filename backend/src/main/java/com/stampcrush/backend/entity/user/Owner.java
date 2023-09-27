package com.stampcrush.backend.entity.user;

import com.stampcrush.backend.application.manager.owner.util.PasswordEncryptor;
import com.stampcrush.backend.application.manager.owner.util.PasswordValidator;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.entity.baseentity.BaseDate;
import com.stampcrush.backend.exception.OwnerUnAuthorizationException;
import jakarta.persistence.*;
import lombok.Builder;
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

    @Column(name = "name")
    private String nickname;
    private String loginId;
    private String encryptedPassword;
    private String phoneNumber;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_provider")
    private OAuthProvider oAuthProvider;

    @Column(name = "oauth_id")
    private Long oAuthId;

    public static Owner of(String loginId, String password) {
        PasswordValidator.validatePassword(password);
        return new Owner(null, loginId, PasswordEncryptor.encrypt(password), null);
    }

    public Owner(Long id, String nickname, String loginId, String encryptedPassword, String phoneNumber) {
        this.id = id;
        this.nickname = nickname;
        this.loginId = loginId;
        this.encryptedPassword = encryptedPassword;
        this.phoneNumber = phoneNumber;
    }

    public Owner(String nickname, String loginId, String encryptedPassword, String phoneNumber) {
        this.nickname = nickname;
        this.loginId = loginId;
        this.encryptedPassword = encryptedPassword;
        this.phoneNumber = phoneNumber;
    }

    @Builder
    public Owner(
            String nickname,
            String email,
            OAuthProvider oAuthProvider,
            Long oAuthId
    ) {
        this.nickname = nickname;
        this.email = email;
        this.oAuthProvider = oAuthProvider;
        this.oAuthId = oAuthId;
    }

    public static void validatePassword(String password) {

    }

    public void checkPassword(String password) {
        String hashedPassword = PasswordEncryptor.encrypt(password);
        if (!this.encryptedPassword.equals(hashedPassword)) {
            throw new OwnerUnAuthorizationException("아이디와 패스워드를 다시 확인 후 로그인해주세요.");
        }
    }
}
