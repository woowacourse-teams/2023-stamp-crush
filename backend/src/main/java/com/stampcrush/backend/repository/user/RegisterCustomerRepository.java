package com.stampcrush.backend.repository.user;

import com.stampcrush.backend.entity.user.RegisterCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterCustomerRepository extends JpaRepository<RegisterCustomer, Integer> {
}
