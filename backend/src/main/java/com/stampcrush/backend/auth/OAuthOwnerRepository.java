package com.stampcrush.backend.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthOwnerRepository extends JpaRepository<OAuthOwner, Long> {

    Optional<OAuthOwner> findByProfileNickname(String nickname);
}
