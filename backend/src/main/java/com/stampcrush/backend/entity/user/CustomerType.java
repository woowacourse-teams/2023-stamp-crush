package com.stampcrush.backend.entity.user;

import com.stampcrush.backend.exception.NotFoundException;

import java.util.Arrays;

public enum CustomerType {

    TEMPORARY("temporary"),
    REGISTER("register");

    private final String type;

    CustomerType(String type) {
        this.type = type;
    }

    public static CustomerType from(String input) {
        return Arrays.stream(CustomerType.values())
                .filter(value -> value.type.equals(input))
                .findAny()
                .orElseThrow(() -> new NotFoundException(String.format("%s 와 일치하는 CustomerType이 없습니다", input)));
    }
}
