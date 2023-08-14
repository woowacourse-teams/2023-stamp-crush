package com.stampcrush.backend.auth;

public interface OAuthInfoResponse {

    String getNickname();

    String getEmail();
    OAuthProvider getOAuthProvider();
}
