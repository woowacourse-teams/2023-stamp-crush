package com.stampcrush.backend.api.cafe.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
public class CafeUpdateRequest {

    @NotNull
    @NotBlank
    private LocalTime openTime;

    @NotNull
    @NotBlank
    private LocalTime closeTime;

    @NotNull
    @NotBlank
    private String telephoneNumber;

    @NotNull
    @NotBlank
    private String cafeImageUrl;
}
