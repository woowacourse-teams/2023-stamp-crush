package com.stampcrush.backend.api.favorites.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FavoritesUpdateRequest {

    @NotNull
    private Boolean isFavorites;
}
