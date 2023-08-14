package com.stampcrush.backend.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)

public class KakaoInfoResponse implements OAuthInfoResponse {

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoAccount {

        private KakaoProfile profile;
        private String email;
        public String gender;
        public String ageRange;
        public LocalDate birthDay;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class KakaoProfile {

        private String nickname;
    }

    @Override
    public String getProfileNickname() {
        return kakaoAccount.email;
    }

    @Override
    public String getGender() {
        return kakaoAccount.gender;
    }

    @Override
    public String getAgeRange() {
        return kakaoAccount.ageRange;
    }

    @Override
    public LocalDate getBirthDay() {
        return kakaoAccount.birthDay;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.KAKAO;
    }
}
