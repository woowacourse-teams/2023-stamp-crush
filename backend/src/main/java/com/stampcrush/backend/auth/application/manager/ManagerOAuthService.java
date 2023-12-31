package com.stampcrush.backend.auth.application.manager;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.application.util.OAuthLoginParams;
import com.stampcrush.backend.auth.client.ManagerOAuthApiClient;
import com.stampcrush.backend.auth.client.OAuthInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Profile("!test")
public class ManagerOAuthService {

    private final String clientId;
    private final String clientSecret;
    private final String baseUri;
    private final String apiUri;
    private final String redirectUri;

    private final Map<OAuthProvider, ManagerOAuthApiClient> clients;

    public ManagerOAuthService(
            @Value("${oauth.kakao.client-id}") String clientId,
            @Value("${oauth.kakao.client-secret}") String clientSecret,
            @Value("${oauth.kakao.redirect-uri-manager}") String redirectUri,
            @Value("${oauth.kakao.url.auth}") String baseUri,
            @Value("${oauth.kakao.url.api}") String apiUri,
            List<ManagerOAuthApiClient> clients
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.baseUri = baseUri;
        this.apiUri = apiUri;
        this.clients = clients.stream().collect(
                Collectors.toUnmodifiableMap(ManagerOAuthApiClient::oAuthProvider, Function.identity())
        );
    }

    public String findLoginRedirectUri() {
        return baseUri
                + "/oauth/authorize"
                + "?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri;
    }

    public OAuthInfoResponse request(OAuthLoginParams params) {
        ManagerOAuthApiClient client = clients.get(params.oAuthProvider());
        String accessToken = client.requestAccessToken(params);

        return client.requestOauthInfo(accessToken);
    }
}
