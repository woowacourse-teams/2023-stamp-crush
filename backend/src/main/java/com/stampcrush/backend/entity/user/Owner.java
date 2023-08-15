package com.stampcrush.backend.entity.user;

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

    @Transient
    private String email;

    @Transient
    private OAuthProvider oAuthProvider;

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
            OAuthProvider oAuthProvider
    ) {
        this.nickname = nickname;
        this.email = email;
        this.oAuthProvider = oAuthProvider;
    }

    public void checkPassword(String encryptedPassword) {
        if (!this.encryptedPassword.equals(encryptedPassword)) {
            throw new OwnerUnAuthorizationException("아이디와 패스워드를 다시 확인 후 로그인해주세요.");
        }
    }
}
