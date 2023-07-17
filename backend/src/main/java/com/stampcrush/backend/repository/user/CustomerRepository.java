package com.stampcrush.backend.repository.user;

import com.stampcrush.backend.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    List<Customer> findByphoneNumber(String phoneNumber);
}
