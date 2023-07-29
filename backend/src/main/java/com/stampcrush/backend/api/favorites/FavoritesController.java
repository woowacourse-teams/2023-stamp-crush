package com.stampcrush.backend.api.favorites;

import com.stampcrush.backend.api.favorites.request.FavoritesUpdateRequest;
import com.stampcrush.backend.application.favorites.FavoritesService;
import com.stampcrush.backend.entity.user.Customer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FavoritesController {

    public final FavoritesService favoritesService;

    @PostMapping("/cafes/{cafeId}/favorites")
    public ResponseEntity<Void> updateFavorites(Customer customer, @PathVariable Long cafeId, @Valid @RequestBody FavoritesUpdateRequest favoritesUpdateRequest) {
        favoritesService.changeFavorites(customer, cafeId, favoritesUpdateRequest.getIsFavorites());
        return ResponseEntity.ok().build();
    }
}
