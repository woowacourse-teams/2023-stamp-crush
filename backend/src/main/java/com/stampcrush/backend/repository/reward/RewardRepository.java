package com.stampcrush.backend.repository.reward;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.reward.Reward;
import com.stampcrush.backend.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RewardRepository extends JpaRepository<Reward, Long> {

    List<Reward> findAllByCustomerIdAndCafeIdAndUsed(Long customerId, Long cafeId, boolean used);

    List<Reward> findAllByCustomerAndUsed(Customer customer, boolean used);

    Long countByCafeAndCustomerAndUsed(Cafe cafe, Customer customer, Boolean used);

    List<Reward> findByCustomer(Customer customer);

    @Modifying
    @Query(value = "update Reward r set r.deleted = true where r.customer_id = :customerId", nativeQuery = true)
    void deleteByCustomer(Long customerId);
}
