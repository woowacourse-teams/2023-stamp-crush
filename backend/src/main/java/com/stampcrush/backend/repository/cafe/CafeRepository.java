package com.stampcrush.backend.repository.cafe;

import com.stampcrush.backend.entity.cafe.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
}
