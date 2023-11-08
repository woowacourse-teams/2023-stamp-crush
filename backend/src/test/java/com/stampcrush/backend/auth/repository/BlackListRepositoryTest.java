package com.stampcrush.backend.auth.repository;

import com.stampcrush.backend.auth.entity.BlackList;
import com.stampcrush.backend.common.KorNamingConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

@KorNamingConverter
@DataJpaTest
class BlackListRepositoryTest {

    @Autowired
    private BlackListRepository blackListRepository;

    @Test
    void 이미_존재하는_리프레쉬_토큰은_false를_반환한다() {
        String refreshToken = "refresh_token";
        blackListRepository.save(new BlackList(refreshToken));
        assertFalse(blackListRepository.isValidRefreshToken(refreshToken));
    }
}
