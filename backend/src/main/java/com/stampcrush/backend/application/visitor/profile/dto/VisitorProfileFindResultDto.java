package com.stampcrush.backend.application.visitor.profile.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VisitorProfileFindResultDto {

    private final Long id;
    private final String nickName;
    private final String phoneNumber;
    private final String email;
}
