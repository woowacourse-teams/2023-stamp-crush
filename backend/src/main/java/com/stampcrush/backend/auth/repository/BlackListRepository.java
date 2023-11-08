package com.stampcrush.backend.auth.repository;

import com.stampcrush.backend.auth.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {

    default boolean existsByInvalidRefreshToken(String refreshToken) {
        return findByInvalidRefreshToken(refreshToken).isPresent();
    }

    Optional<BlackList> findByInvalidRefreshToken(String refreshToken);
}
