package com.stampcrush.backend.repository.cafe;

import com.stampcrush.backend.entity.cafe.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeRepository extends JpaRepository<Cafe, Long> {

    List<Cafe> findAllByOwnerId(Long ownerId);
}
