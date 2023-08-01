package com.stampcrush.backend.api.manager.customer.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TemporaryCustomerCreateRequest {

    @NotNull
    @NotBlank
    private String phoneNumber;
}
