package com.stampcrush.backend.api.visitor.profile.response;

import com.stampcrush.backend.application.visitor.profile.dto.VisitorPhoneNumberFindResultDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VisitorPhoneNumberFindResponse {

    private final Boolean isPhoneNumberRegistered;

    public static VisitorPhoneNumberFindResponse from(VisitorPhoneNumberFindResultDto dto) {
        if (dto.getPhoneNumber() == null) {
            return new VisitorPhoneNumberFindResponse(false);
        }
        return new VisitorPhoneNumberFindResponse(true);
    }
}
