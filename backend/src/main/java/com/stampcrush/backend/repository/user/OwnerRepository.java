package com.stampcrush.backend.repository.user;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.entity.user.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findByLoginId(String loginId);

    Optional<Owner> findByNickname(String nickname);

    @Query("SELECT o FROM Owner o WHERE o.oAuthProvider = :oAuthProvider AND o.oAuthId = :oAuthId")
    Optional<Owner> findByOAuthProviderAndOAuthId(
            @Param("oAuthProvider") OAuthProvider oAuthProvider,
            @Param("oAuthId") Long oAuthId
    );
}
