package com.stampcrush.backend.auth.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
public class ManagerOAuthService {

    private final String clientId;
    private final String clientSecret;
    private final String baseUri;
    private final String apiUri;
    private final String redirectUri;

    public ManagerOAuthService(
            @Value("${oauth.kakao.client-id}") String clientId,
            @Value("${oauth.kakao.client-secret}") String clientSecret,
            @Value("${oauth.kakao.redirect-uri}") String redirectUri,
            @Value("${oauth.kakao.url.auth}") String baseUri,
            @Value("${oauth.kakao.url.api}") String apiUri
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.baseUri = baseUri;
        this.apiUri = apiUri;
    }

    public String findLoginRedirectUri() {
        return baseUri + "/oauth/authorize?"
                + "response_type=code"
                + "client_id=" + clientId
                + "redirect_uri" + redirectUri;
    }
}
