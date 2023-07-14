package com.stampcrush.backend.repository.user;

import com.stampcrush.backend.entity.user.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
