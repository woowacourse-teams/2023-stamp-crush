package com.stampcrush.backend.repository.favorites;

import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.favorites.Favorites;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.fixture.CustomerFixture;
import com.stampcrush.backend.fixture.OwnerFixture;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@KorNamingConverter
@DataJpaTest
class FavoritesRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FavoritesRepository favoritesRepository;

    @Test
    void 해당_고객의_즐겨찾기를_삭제한다() {
        // given
        Cafe cafe = createCafe(OwnerFixture.GITCHAN);
        Customer deletedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);
        Customer noDeletedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_1);

        favoritesRepository.save(new Favorites(cafe, deletedCustomer));
        favoritesRepository.save(new Favorites(cafe, noDeletedCustomer));

        // when
        favoritesRepository.deleteByCustomer(deletedCustomer.getId());

        // then
        assertAll(
                () -> assertThat(favoritesRepository.findByCustomer(deletedCustomer)).isEmpty(),
                () -> assertThat(favoritesRepository.findByCustomer(noDeletedCustomer)).isNotEmpty()
        );
    }

    private Cafe createCafe(Owner owner) {
        Owner savedOwner = ownerRepository.save(owner);
        return cafeRepository.save(
                new Cafe(
                        "깃짱카페",
                        "서초구",
                        "어쩌고",
                        "0101010101",
                        savedOwner
                )
        );
    }
}
