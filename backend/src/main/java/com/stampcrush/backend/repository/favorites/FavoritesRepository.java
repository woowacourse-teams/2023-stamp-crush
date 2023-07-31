package com.stampcrush.backend.repository.favorites;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.favorites.Favorites;
import com.stampcrush.backend.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    Optional<Favorites> findByCafeAndCustomer(Cafe cafe, Customer customer);
}
