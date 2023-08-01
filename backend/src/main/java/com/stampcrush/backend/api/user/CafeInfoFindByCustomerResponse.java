package com.stampcrush.backend.api.user;

import com.stampcrush.backend.api.admin.cafe.response.CafeInfoFindResponse;
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
