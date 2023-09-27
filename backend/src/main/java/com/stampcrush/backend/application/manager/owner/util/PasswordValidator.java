package com.stampcrush.backend.application.manager.owner.util;

import com.stampcrush.backend.exception.PasswordBadRequestException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    private static final Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)[A-Za-z\\d!@#$%^*+=-]{8,30}$");

    public static void validatePassword(String password) {
        Matcher matcher = pattern.matcher(password);
        boolean isNotMatched = !matcher.find();
        if (isNotMatched) {
            throw new PasswordBadRequestException("비밀번호 형식을 다시 확인해주세요.");
        }
    }
}
