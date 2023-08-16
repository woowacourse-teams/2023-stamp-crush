package com.stampcrush.backend.auth.application.util;

import com.stampcrush.backend.auth.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {

    OAuthProvider oAuthProvider();

    MultiValueMap<String, String> makeBody();
}
