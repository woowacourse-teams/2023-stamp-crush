package com.stampcrush.backend.repository.visithistory;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.visithistory.VisitHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitHistoryRepository extends JpaRepository<VisitHistory, Long> {

    List<VisitHistory> findByCafeAndCustomer(Cafe cafe, Customer customer);

    List<VisitHistory> findVisitHistoriesByCustomer(Customer customer);

    List<VisitHistory> findByCafeIdAndCustomerId(Long cafeId, Long customerId);

    List<VisitHistory> findByCustomer(Customer customer);
}
