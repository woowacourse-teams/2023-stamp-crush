package com.stampcrush.backend.auth.repository;

import com.stampcrush.backend.auth.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {

    @Query("SELECT CASE WHEN COUNT(bl) > 0 THEN false ELSE true END FROM BlackList bl WHERE bl.invalidRefreshToken = :refreshToken")
    boolean isValidRefreshToken(String refreshToken);
}
