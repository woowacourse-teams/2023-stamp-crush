package com.stampcrush.backend.auth.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokensResponse {

    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;

    public static AuthTokensResponse of(String accessToken, String refreshToken, String grantType, Long expiresIn) {
        return new AuthTokensResponse(accessToken, refreshToken, grantType, expiresIn);
    }
}
