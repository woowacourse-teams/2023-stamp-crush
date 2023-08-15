package com.stampcrush.backend.repository.user;

import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.fixture.OwnerFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void Owner를_닉네임으로_조회한다() {
        // given
        Owner owner = OwnerFixture.GITCHAN;
        Owner savedOwner = ownerRepository.save(owner);

        // when
        Owner findOwner = ownerRepository.findByNickname(savedOwner.getNickname()).get();

        // then
        assertThat(savedOwner).isEqualTo(findOwner);
    }
}
