package com.stampcrush.backend.api.manager.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ManagerAuthService {

    public String createLoginRedirectUri() {
        String baseUri = "https://nid.naver.com/oauth2.0/authorize?";

        String stateToken = StateUtils.generateState();
        String state = StateUtils.urlEncode(stateToken);

        return baseUri + "response_type=code&"
                + "client_id=7RLq6nq_iPu2JjNluOWm&"
                + "redirect_uri=https://stampcrush.site/api/owners/oauth/naver"
                + "state=" + state;
    }
}
