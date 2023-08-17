package com.stampcrush.backend.api.visitor.profile.request;

import lombok.Getter;

@Getter
public final class VisitorProfilesPhoneNumberUpdateRequest {

    private String phoneNumber;

    public VisitorProfilesPhoneNumberUpdateRequest(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public VisitorProfilesPhoneNumberUpdateRequest() {
    }
}
