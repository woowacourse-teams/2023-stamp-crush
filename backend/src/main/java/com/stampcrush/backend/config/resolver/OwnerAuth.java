package com.stampcrush.backend.config.resolver;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OwnerAuth {

    private final Long id;
    private final String name;
    private final String loginId;
    private final String encryptedPassword;
    private final String phoneNumber;
}
