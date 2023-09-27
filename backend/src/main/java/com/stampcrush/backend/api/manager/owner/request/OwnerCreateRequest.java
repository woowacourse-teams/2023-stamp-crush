package com.stampcrush.backend.api.manager.owner.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OwnerCreateRequest {

    private String loginId;
    private String password;
}
