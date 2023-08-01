package com.stampcrush.backend.application.manager.favorites;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.favorites.Favorites;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.exception.CafeNotFoundException;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.favorites.FavoritesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final CafeRepository cafeRepository;

    public void changeFavorites(Customer customer, Long cafeId, Boolean isFavorites) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CafeNotFoundException("카페를 찾을 수 없습니다."));
        Optional<Favorites> findFavorites = favoritesRepository.findByCafeAndCustomer(cafe, customer);
        if (findFavorites.isPresent()) {
            findFavorites.get().changeFavorites(isFavorites);
            return;
        }
        favoritesRepository.save(new Favorites(cafe, customer, isFavorites));
    }
}
