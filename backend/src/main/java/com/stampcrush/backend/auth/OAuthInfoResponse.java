package com.stampcrush.backend.auth;

import java.time.LocalDate;

public interface OAuthInfoResponse {

    String getProfileNickname();

    String getGender();

    String getAgeRange();

    LocalDate getBirthDay();

    OAuthProvider getOAuthProvider();
}
