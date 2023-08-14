package com.stampcrush.backend.auth;

public interface OAuthApiClient {

    OAuthProvider oAuthProvider();

    String requestAccessToken(OAuthLoginParams params);

    OAuthInfoResponse requestOauthInfo(String accessToken);
}
