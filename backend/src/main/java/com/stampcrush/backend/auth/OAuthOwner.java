package com.stampcrush.backend.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class OAuthOwner {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String profileNickname;
    private String email;
    private OAuthProvider oAuthProvider;

    public OAuthOwner() {
    }

    @Builder
    public OAuthOwner(
            String profileNickname,
            String email,
            OAuthProvider oAuthProvider
    ) {
        this.profileNickname = profileNickname;
        this.email = email;
        this.oAuthProvider = oAuthProvider;
    }
}
