package com.stampcrush.backend.repository.user;

import com.stampcrush.backend.entity.user.TemporaryCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemporaryCustomerRepository extends JpaRepository<TemporaryCustomer, Long> {
}
