package com.stampcrush.backend.service;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TemporaryCustomerRequest {

    @NotNull
    private String phoneNumber;
}
