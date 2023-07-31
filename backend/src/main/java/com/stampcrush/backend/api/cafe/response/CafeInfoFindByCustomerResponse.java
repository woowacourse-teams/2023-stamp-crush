package com.stampcrush.backend.api.cafe.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Getter
public class CafeInfoFindByCustomerResponse {

    private CafeInfoFindResponse cafe;
}
