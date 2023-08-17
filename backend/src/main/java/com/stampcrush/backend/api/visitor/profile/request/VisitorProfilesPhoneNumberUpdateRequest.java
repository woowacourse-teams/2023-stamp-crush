package com.stampcrush.backend.api.visitor.profile.request;

public class VisitorProfilesPhoneNumberUpdateRequest {

    private String phoneNumber;

    public VisitorProfilesPhoneNumberUpdateRequest(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public VisitorProfilesPhoneNumberUpdateRequest() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
