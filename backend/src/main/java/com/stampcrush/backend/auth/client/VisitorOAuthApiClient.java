package com.stampcrush.backend.auth.client;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.application.util.OAuthLoginParams;

public interface VisitorOAuthApiClient {

    OAuthProvider oAuthProvider();

    String requestAccessToken(OAuthLoginParams params);

    OAuthInfoResponse requestOauthInfo(String accessToken);
}
