package com.stampcrush.backend.auth.client;

import com.stampcrush.backend.auth.OAuthProvider;

public interface OAuthInfoResponse {

    Long getId();

    String getNickname();

    String getEmail();

    OAuthProvider getOAuthProvider();
}
