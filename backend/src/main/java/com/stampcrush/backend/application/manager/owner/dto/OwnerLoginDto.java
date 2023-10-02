package com.stampcrush.backend.application.manager.owner.dto;

import com.stampcrush.backend.api.manager.owner.request.OwnerLoginRequest;
import lombok.Getter;

@Getter
public class OwnerLoginDto {

    private String loginId;
    private String password;

    public OwnerLoginDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public static OwnerLoginDto from(OwnerLoginRequest ownerLoginRequest) {
        return new OwnerLoginDto(
                ownerLoginRequest.getLoginId(),
                ownerLoginRequest.getPassword());
    }
}
