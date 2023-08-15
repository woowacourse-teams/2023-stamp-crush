package com.stampcrush.backend.auth.client;

import com.stampcrush.backend.auth.OAuthProvider;

public interface OAuthInfoResponse {

    String getNickname();

    String getEmail();

    OAuthProvider getOAuthProvider();

    Long getOAuthId();
}
