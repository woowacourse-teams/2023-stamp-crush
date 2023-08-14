package com.stampcrush.backend.auth;

import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {

    OAuthProvider oAuthProvider();

    MultiValueMap<String, String> makeBody();
}
