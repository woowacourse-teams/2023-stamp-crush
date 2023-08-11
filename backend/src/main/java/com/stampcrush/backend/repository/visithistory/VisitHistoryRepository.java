package com.stampcrush.backend.repository.visithistory;

import com.stampcrush.backend.entity.visithistory.VisitHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitHistoryRepository extends JpaRepository<VisitHistory, Long> {
}
