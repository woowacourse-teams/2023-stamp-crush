package com.stampcrush.backend.api;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OwnerAuth {

    private final Long id;
    private final String name;
    private final String loginId;
    private final String encryptedPassword;
    private final String phoneNumber;
}
