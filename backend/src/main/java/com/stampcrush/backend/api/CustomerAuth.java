package com.stampcrush.backend.api;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerAuth {

    private final Long id;
    private final String nickname;
    private final String phoneNumber;
    private final String loginId;
    private final String encryptedPassword;
}
