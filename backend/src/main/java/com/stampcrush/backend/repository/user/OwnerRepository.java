package com.stampcrush.backend.repository.user;

import com.stampcrush.backend.entity.user.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findByLoginId(String loginId);
}
