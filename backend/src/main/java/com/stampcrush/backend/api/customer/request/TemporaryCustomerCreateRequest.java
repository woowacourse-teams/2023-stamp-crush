package com.stampcrush.backend.api.customer.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TemporaryCustomerCreateRequest {

    @NotNull
    private String phoneNumber;
}
