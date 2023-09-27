package com.stampcrush.backend.application.manager.owner.dto;

import com.stampcrush.backend.api.manager.owner.request.OwnerCreateRequest;
import lombok.Getter;

@Getter
public class OwnerCreateDto {

    private String loginId;
    private String password;

    public OwnerCreateDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public static OwnerCreateDto from(OwnerCreateRequest ownerCreateRequest) {
        return new OwnerCreateDto(
                ownerCreateRequest.getLoginId(),
                ownerCreateRequest.getPassword());
    }
}
