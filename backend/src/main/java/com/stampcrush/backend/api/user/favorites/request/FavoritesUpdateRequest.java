package com.stampcrush.backend.api.user.favorites.request;

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
