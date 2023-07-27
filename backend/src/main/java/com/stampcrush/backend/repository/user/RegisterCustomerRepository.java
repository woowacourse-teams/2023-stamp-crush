package com.stampcrush.backend.repository.user;

import com.stampcrush.backend.entity.user.RegisterCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterCustomerRepository extends JpaRepository<RegisterCustomer, Integer> {

    Optional<RegisterCustomer> findByLoginId(String loginId);
}
