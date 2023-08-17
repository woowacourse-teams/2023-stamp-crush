package com.stampcrush.backend.api.visitor.profile.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VisitorProfilesPhoneNumberUpdateRequest {

    @NotNull
    @NotBlank
    private String phoneNumber;
}
