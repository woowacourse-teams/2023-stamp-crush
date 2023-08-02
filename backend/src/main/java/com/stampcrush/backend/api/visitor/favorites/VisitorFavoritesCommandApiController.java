package com.stampcrush.backend.api.visitor.favorites;

import com.stampcrush.backend.api.visitor.favorites.request.FavoritesUpdateRequest;
import com.stampcrush.backend.application.visitor.favorites.VisitorFavoritesCommandService;
import com.stampcrush.backend.config.resolver.CustomerAuth;
import com.stampcrush.backend.entity.user.Customer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class VisitorFavoritesCommandApiController {

    public final VisitorFavoritesCommandService visitorFavoritesCommandService;

    @PostMapping("/cafes/{cafeId}/favorites")
    public ResponseEntity<Void> updateFavorites(
            CustomerAuth customer,
            @PathVariable Long cafeId,
            @Valid @RequestBody FavoritesUpdateRequest favoritesUpdateRequest
    ) {
        visitorFavoritesCommandService.changeFavorites(customer.getId(), cafeId, favoritesUpdateRequest.getIsFavorites());
        return ResponseEntity.ok().build();
    }
}
