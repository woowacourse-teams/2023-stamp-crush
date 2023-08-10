package com.stampcrush.backend.repository.visithistory;

import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.visithistory.VisitHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitHistoryRepository extends JpaRepository<VisitHistory, Long> {

    List<VisitHistory> findVisitHistoriesByCustomer(Customer customer);
}
