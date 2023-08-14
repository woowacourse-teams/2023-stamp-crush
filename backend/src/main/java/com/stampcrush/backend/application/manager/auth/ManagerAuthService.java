package com.stampcrush.backend.application.manager.auth;

import com.stampcrush.backend.api.manager.auth.StateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ManagerAuthService {

    public String createNaverLoginRedirectUri() {
        String baseUri = "https://nid.naver.com/oauth2.0/authorize?";

        String stateToken = StateUtils.generateState();
        String state = StateUtils.urlEncode(stateToken);

        return baseUri + "response_type=code&"
                + "client_id=7RLq6nq_iPu2JjNluOWm&"
                + "redirect_uri=https://stampcrush.site/api/owners/oauth/naver"
                + "state=" + state;
    }

    public String createKakaoLoginRedirectUri() {
        String clientId = "70e2bd57133efde3171725c762b61188";
        String redirectUri = "https://stampcrush.site/owners/oauth/kakao";
        return "https://kauth.kakao.com/oauth/authorize?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=" + "code";
    }
}
