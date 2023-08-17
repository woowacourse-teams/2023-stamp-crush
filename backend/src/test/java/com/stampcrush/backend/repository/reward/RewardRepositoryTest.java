package com.stampcrush.backend.repository.reward;

import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.reward.Reward;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.fixture.CustomerFixture;
import com.stampcrush.backend.fixture.OwnerFixture;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@KorNamingConverter
@DataJpaTest
class RewardRepositoryTest {

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void 해당_고객의_사용_가능한_리워드를_조회한다() {
        Cafe cafe = createCafe(OwnerFixture.GITCHAN);
        Customer customer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);

        Reward reward = new Reward("깃짱카페의 아메리카노", customer, cafe);
        Reward savedReward = rewardRepository.save(reward);

        List<Reward> findRewards = rewardRepository.findAllByCustomerAndUsed(customer, false);

        assertThat(findRewards).containsOnly(savedReward);
    }

    @Test
    void 해당_고객의_사용_완료한_리워드를_조회한다() {
        Cafe cafe = createCafe(OwnerFixture.GITCHAN);
        Customer customer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);

        Reward reward = new Reward("깃짱카페의 아메리카노", customer, cafe);
        Reward savedReward = rewardRepository.save(reward);
        savedReward.useReward(customer, cafe);

        List<Reward> findRewards = rewardRepository.findAllByCustomerAndUsed(customer, false);

        assertThat(findRewards).isEmpty();
    }

    @Test
    void 해당_고객의_사용_가능한_리워드의_개수를_조회한다() {
        // given
        Cafe cafe = createCafe(OwnerFixture.GITCHAN);
        Customer customer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);

        Reward reward1 = rewardRepository.save(new Reward("Reward1", customer, cafe));
        rewardRepository.save(new Reward("Reward2", customer, cafe));
        rewardRepository.save(new Reward("Reward3", customer, cafe));
        rewardRepository.save(new Reward("Reward4", customer, cafe));
        rewardRepository.save(new Reward("Reward5", customer, cafe));
        reward1.useReward(customer, cafe);

        // when
        Long countOfUnusedReward = rewardRepository.countByCafeAndCustomerAndUsed(cafe, customer, Boolean.FALSE);

        // then
        assertThat(countOfUnusedReward).isEqualTo(4);
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
