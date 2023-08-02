package com.stampcrush.backend.application.visitor.favorites;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.favorites.Favorites;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.exception.CafeNotFoundException;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.favorites.FavoritesRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class VisitorFavoritesCommandService {

    private final FavoritesRepository favoritesRepository;
    private final CafeRepository cafeRepository;
    private final CustomerRepository customerRepository;

    public void changeFavorites(Long customerId, Long cafeId, Boolean isFavorites) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CafeNotFoundException("카페를 찾을 수 없습니다."));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("고객을 찾을 수 없습니다"));
        Optional<Favorites> findFavorites = favoritesRepository.findByCafeAndCustomer(cafe, customer);
        if (findFavorites.isPresent()) {
            findFavorites.get().changeFavorites(isFavorites);
            return;
        }
        favoritesRepository.save(new Favorites(cafe, customer, isFavorites));
    }
}
