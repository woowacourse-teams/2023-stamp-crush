package com.stampcrush.backend.repository.user;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByPhoneNumber(String phoneNumber);

    Optional<Customer> findByLoginId(String loginId);

    @Query("SELECT c FROM Customer c WHERE c.oAuthProvider = :oAuthProvider AND c.oAuthId = :oAuthId")
    Optional<Customer> findByOAuthProviderAndOAuthId(
            @Param("oAuthProvider") OAuthProvider oAuthProvider,
            @Param("oAuthId") Long oAuthId
    );
}
