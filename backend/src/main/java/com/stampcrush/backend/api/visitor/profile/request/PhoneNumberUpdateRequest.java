package com.stampcrush.backend.api.visitor.profile.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class PhoneNumberUpdateRequest {

    private final String phoneNumber;
}
