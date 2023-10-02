package com.stampcrush.backend.application.manager.owner.util;

import com.stampcrush.backend.exception.PasswordBadRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;

class PasswordValidatorTest {

    @Test
    void 비밀번호_형식은_숫자_알파벳을_한_개_이상_포함해야한다() {
        // given
        String password = "a4rr4a!!dsad";

        // when, then
        assertThatNoException()
                .isThrownBy(() -> PasswordValidator.validatePassword(password));
    }

    @Test
    void 비밀번호에_알파벳이_없으면_예외를_던진다() {
        // given
        String password = "532532!!";

        // when, then
        Assertions.assertThatThrownBy(() -> PasswordValidator.validatePassword(password))
                .isInstanceOf(PasswordBadRequestException.class);
    }

    @Test
    void 비밀번호에_숫자가_없으면_예외를_던진다() {
        // given
        String password = "@@adADadas!!";

        // when, then
        Assertions.assertThatThrownBy(() -> PasswordValidator.validatePassword(password))
                .isInstanceOf(PasswordBadRequestException.class);
    }
}
