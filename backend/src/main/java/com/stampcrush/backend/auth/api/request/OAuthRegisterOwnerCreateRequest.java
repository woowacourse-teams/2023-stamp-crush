package com.stampcrush.backend.auth.api.request;

import com.stampcrush.backend.auth.OAuthProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OAuthRegisterOwnerCreateRequest {

    private final String nickname;
    private final OAuthProvider oAuthProvider;
    private final Long oAuthId;
}
