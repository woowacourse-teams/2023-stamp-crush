package com.stampcrush.backend.entity.user;

import com.stampcrush.backend.entity.baseentity.BaseDate;
import com.stampcrush.backend.exception.OwnerUnAuthorizationException;
import jakarta.persistence.Column;
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

    @Column(name = "name")
    private String nickname;
    private String loginId;
    private String encryptedPassword;
    private String phoneNumber;

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

    public void checkPassword(String encryptedPassword) {
        if (!this.encryptedPassword.equals(encryptedPassword)) {
            throw new OwnerUnAuthorizationException("아이디와 패스워드를 다시 확인 후 로그인해주세요.");
        }
    }
}
