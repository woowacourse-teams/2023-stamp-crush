package com.stampcrush.backend.application.visitor.favorites;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.favorites.Favorites;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.repository.favorites.FavoritesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VisitorFavoritesFindService {

    private final FavoritesRepository favoritesRepository;

    public boolean findIsFavorites(Cafe cafe, Customer customer) {
        Optional<Favorites> favorites = favoritesRepository.findByCafeAndCustomer(cafe, customer);

        if (favorites.isEmpty()) {
            return false;
        }

        return favorites.get().getIsFavorites();
    }
}
