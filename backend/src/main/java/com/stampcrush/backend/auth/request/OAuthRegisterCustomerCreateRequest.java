package com.stampcrush.backend.auth.request;

import com.stampcrush.backend.auth.OAuthProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OAuthRegisterCustomerCreateRequest {

    private final String nickname;
    private final String email;
    private final OAuthProvider oAuthProvider;
    private final Long oAuthId;
}
