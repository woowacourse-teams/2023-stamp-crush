package com.stampcrush.backend.application.visitor.favorites;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.favorites.Favorites;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.exception.CafeNotFoundException;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.favorites.FavoritesRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ServiceSliceTest
public class VisitorFavoritesCommandServiceTest {

    @InjectMocks
    private VisitorFavoritesCommandService visitorFavoritesCommandService;

    @Mock
    private CafeRepository cafeRepository;

    @Mock
    private FavoritesRepository favoritesRepository;

    @Test
    void 존재하지_않는_카페를_즐겨찾기에_등록_또는_해제_하려하면_예외를_던진다() {
        // given
        Customer customer = Customer.registeredCustomerBuilder()
                .nickname("hardy")
                .phoneNumber("01000000000")
                .build();

        Long cafeId = 1L;
        Boolean isFavorites = Boolean.TRUE;

        when(cafeRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> visitorFavoritesCommandService.changeFavorites(customer.getId(), cafeId, isFavorites))
                .isInstanceOf(CafeNotFoundException.class);
    }

    @Test
    @Disabled
        // TODO: 하디는 해결하시오
    void 카페를_즐겨찾기_목록에_이미_존재하지_않을_경우_새로_만들어_저장한다() {
        // given
        Customer customer = Customer.registeredCustomerBuilder()
                .nickname("hardy")
                .phoneNumber("01000000000")
                .build();
        Long cafeId = 1L;
        Boolean isFavorites = Boolean.TRUE;
        Cafe cafe = new Cafe("하디카페", "동작구", "이수동", "1111", new Owner("하디", "hardy@", "1234", "010111111111"));

        when(cafeRepository.findById(anyLong()))
                .thenReturn(Optional.of(cafe));
        when(favoritesRepository.findByCafeAndCustomer(cafe, customer))
                .thenReturn(Optional.empty());

        // when
        visitorFavoritesCommandService.changeFavorites(customer.getId(), cafeId, isFavorites);

        // then
        verify(cafeRepository, times(1)).findById(anyLong());
        verify(favoritesRepository, times(1)).findByCafeAndCustomer(cafe, customer);
        verify(favoritesRepository, times(1)).save(any(Favorites.class));
    }

    @Test
    @Disabled
        // TODO: 하디는 해결하시오
    void 카페를_즐겨찾기_목록에_이미_존재할_경우_새로_저장하지_않고_변경한다() {
        // given
        Customer customer = Customer.registeredCustomerBuilder()
                .nickname("hardy")
                .phoneNumber("01000000000")
                .build();
        Long cafeId = 1L;
        Boolean isFavorites = Boolean.TRUE;
        Cafe cafe = new Cafe("하디카페", "동작구", "이수동", "1111", new Owner("하디", "hardy@", "1234", "010111111111"));
        Favorites favorites = new Favorites(cafe, customer, isFavorites);

        when(cafeRepository.findById(anyLong()))
                .thenReturn(Optional.of(cafe));
        when(favoritesRepository.findByCafeAndCustomer(cafe, customer))
                .thenReturn(Optional.of(favorites));

        // when
        visitorFavoritesCommandService.changeFavorites(customer.getId(), cafeId, isFavorites);

        // then
        verify(cafeRepository, times(1)).findById(anyLong());
        verify(favoritesRepository, times(1)).findByCafeAndCustomer(cafe, customer);
        verify(favoritesRepository, times(0)).save(any(Favorites.class));
    }
}
