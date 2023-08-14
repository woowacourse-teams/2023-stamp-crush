package com.stampcrush.backend.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class OAuthOwner {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String profileNickname;
    private String gender;
    private String ageRange;
    private LocalDate birthDay;
    private OAuthProvider oAuthProvider;

    public OAuthOwner() {
    }

    @Builder
    public OAuthOwner(
            String profileNickname,
            String gender,
            String ageRange,
            LocalDate birthDay,
            OAuthProvider oAuthProvider
    ) {
        this.profileNickname = profileNickname;
        this.gender = gender;
        this.ageRange = ageRange;
        this.birthDay = birthDay;
        this.oAuthProvider = oAuthProvider;
    }
}
