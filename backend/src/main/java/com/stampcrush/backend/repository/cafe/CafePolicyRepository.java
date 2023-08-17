package com.stampcrush.backend.repository.cafe;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CafePolicyRepository extends JpaRepository<CafePolicy, Long> {

    Optional<CafePolicy> findByCafe(Cafe cafe);

    List<CafePolicy> findByCafeAndCreatedAtGreaterThan(Cafe cafe, LocalDateTime createdAt);
}
