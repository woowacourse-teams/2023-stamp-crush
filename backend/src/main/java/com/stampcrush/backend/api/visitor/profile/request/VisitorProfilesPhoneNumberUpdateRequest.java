package com.stampcrush.backend.api.visitor.profile.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class VisitorProfilesPhoneNumberUpdateRequest {

    private final String phoneNumber;
}
