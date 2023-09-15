package com.stampcrush.backend.auth.api.request;

import com.stampcrush.backend.auth.OAuthProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OAuthRegisterOwnerCreateRequest {

    private final String nickname;
    private final OAuthProvider oAuthProvider;
    private final Long oAuthId;

    public String getNickname() {
        return nickname;
    }

    public OAuthProvider getoAuthProvider() {
        return oAuthProvider;
    }

    public Long getoAuthId() {
        return oAuthId;
    }
}
