package com.stampcrush.backend.config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class LogFormat {

    public static final String OWNER_UNAUTHORIZATION_LOG_FORMAT = "사장 아이디: {}, 인증에 실패했습니다.";
    public static final String CUSTOMER_UNAUTHORIZATION_LOG_FORMAT = "고객 아이디: {}, 인증에 실패했습니다.";
    public static final String NOT_FOUND_LOG_FORMAT = "자원: {}, 아이디: {}, 존재하지 않습니다.";
    public static final String TEMPORARY_USER_LOG_FORMAT = "임시 유저 아이디: {}, {}";

    private LogFormat() {
    }
}
