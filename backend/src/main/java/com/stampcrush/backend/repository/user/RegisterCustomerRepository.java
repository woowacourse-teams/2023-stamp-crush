package com.stampcrush.backend.repository.user;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RegisterCustomerRepository extends JpaRepository<RegisterCustomer, Integer> {

    Optional<RegisterCustomer> findByLoginId(String loginId);

    Optional<RegisterCustomer> findByNickname(String nickname);

    @Query("SELECT c FROM RegisterCustomer c WHERE c.oAuthProvider = :oAuthProvider AND c.oAuthId = :oAuthId")
    Optional<RegisterCustomer> findByOAuthProviderAndOAuthId(@Param("oAuthProvider") OAuthProvider oAuthProvider, @Param("oAuthId") Long oAuthId);
}
