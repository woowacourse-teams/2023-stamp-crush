package com.stampcrush.backend.repository.favorites;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.favorites.Favorites;
import com.stampcrush.backend.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    Optional<Favorites> findByCafeAndCustomer(Cafe cafe, Customer customer);

    List<Favorites> findByCustomer(Customer customer);

    @Modifying
    @Query(value = "update Favorites f set f.deleted = true where f.customer_id = :customerId", nativeQuery = true)
    void deleteByCustomer(Long customerId);
}
