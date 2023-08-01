package com.stampcrush.backend.application.manager.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CustomersFindResultDto {

    private List<CustomerFindDto> customer;
}
