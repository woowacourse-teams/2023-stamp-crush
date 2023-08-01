package com.stampcrush.backend.api.manager.cafe.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CafeCreateRequest {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String roadAddress;

    @NotNull
    @NotBlank
    private String detailAddress;

    @NotNull
    @NotBlank
    private String businessRegistrationNumber;
}
