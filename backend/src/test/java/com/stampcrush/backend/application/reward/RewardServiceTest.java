package com.stampcrush.backend.application.reward;

import com.stampcrush.backend.application.reward.dto.RewardUsedUpdate;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.reward.Reward;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.reward.RewardRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import com.stampcrush.backend.repository.user.TemporaryCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class RewardServiceTest {

    @Autowired
    private RewardService rewardService;

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private RegisterCustomerRepository registerCustomerRepository;

    @Autowired
    private TemporaryCustomerRepository temporaryCustomerRepository;

    private Owner owner_1;
    private Owner owner_2;
    private Cafe cafe_1;
    private Cafe cafe_2;
    private Customer registerCustomer_1;
    private Customer registerCustomer_2;
    private Customer temporaryCustomer;
    private Reward reward;

    @BeforeEach
    void setUp() {
        owner_1 = ownerRepository.save(new Owner("lisa", "lisa@naver.com", "1234", "01011111111"));
        owner_2 = ownerRepository.save(new Owner("tommy", "tommy@naver.com", "5678", "01099999999"));

        cafe_1 = cafeRepository.save(new Cafe("stamp-crush", LocalTime.of(18, 0), LocalTime.of(23, 59), "0211111111", "imageUrl", "잠실도로명", "14층", "11-11111", owner_1));
        cafe_2 = cafeRepository.save(new Cafe("wrongCafe", LocalTime.of(18, 0), LocalTime.of(23, 59), "0211111111", "imageUrl", "잠실도로", "1층", "11-11111", owner_2));

        registerCustomer_1 = registerCustomerRepository.save(new RegisterCustomer("registered", "01022222222", "ehdgur@naver.com", "1111"));
        registerCustomer_2 = registerCustomerRepository.save(new RegisterCustomer("registered2", "01044444444", "dsadsa@naver.com", "2345"));
        temporaryCustomer = temporaryCustomerRepository.save(new TemporaryCustomer("temporary", "01033333333"));

        reward = rewardRepository.save(new Reward("Americano", false, registerCustomer_1, cafe_1));
    }

    @Test
    void 리워드를_사용한다() {
        // given
        RewardUsedUpdate rewardUsedUpdate = new RewardUsedUpdate(reward.getId(), registerCustomer_1.getId(), cafe_1.getId(), true);

        // when
        rewardService.updateUsed(rewardUsedUpdate);

        // then
        assertThat(reward.getUsed()).isTrue();
    }

    @Test
    void 존재하지_않는_리워드를_사용하려하면_예외를_던진다() {
        // given
        RewardUsedUpdate rewardUsedUpdate = new RewardUsedUpdate(reward.getId(), Long.MAX_VALUE, cafe_1.getId(), true);

        // when, then
        assertThatThrownBy(() -> rewardService.updateUsed(rewardUsedUpdate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 존재하지_않는_고객에_대한_리워드를_사용하려하면_예외를_던진다() {
        // given
        RewardUsedUpdate rewardUsedUpdate = new RewardUsedUpdate(Long.MAX_VALUE, registerCustomer_1.getId(), cafe_1.getId(), true);

        // when, then
        assertThatThrownBy(() -> rewardService.updateUsed(rewardUsedUpdate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 존재하지_않는_카페에서_리워드를_사용하려하면_예외를_던진다() {
        // given
        RewardUsedUpdate rewardUsedUpdate = new RewardUsedUpdate(reward.getId(), registerCustomer_1.getId(), Long.MAX_VALUE, true);

        // when, then
        assertThatThrownBy(() -> rewardService.updateUsed(rewardUsedUpdate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 임시회원이_리워드를_사용하려하면_예외를_던진다() {
        //given
        RewardUsedUpdate rewardUsedUpdate = new RewardUsedUpdate(reward.getId(), temporaryCustomer.getId(), cafe_1.getId(), true);

        // when, then
        assertThatThrownBy(() -> rewardService.updateUsed(rewardUsedUpdate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이미_사용된_리워드를_사용려하면_예외를_던진다() {
        //given
        RewardUsedUpdate rewardUsedUpdate = new RewardUsedUpdate(reward.getId(), registerCustomer_1.getId(), cafe_1.getId(), true);
        rewardService.updateUsed(rewardUsedUpdate);

        // when, then
        assertThatThrownBy(() -> rewardService.updateUsed(rewardUsedUpdate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 다른_카페에의해_생성된_리워드를_사용하려하면_예외를_던진다() {
        //given
        RewardUsedUpdate rewardUsedUpdate = new RewardUsedUpdate(reward.getId(), registerCustomer_1.getId(), cafe_2.getId(), true);

        // when, then
        assertThatThrownBy(() -> rewardService.updateUsed(rewardUsedUpdate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 소유자가_아닌_고객이_리워드를_사용하려하면_예외를_던진다() {
        //given
        RewardUsedUpdate rewardUsedUpdate = new RewardUsedUpdate(reward.getId(), registerCustomer_2.getId(), cafe_1.getId(), true);

        // when, then
        assertThatThrownBy(() -> rewardService.updateUsed(rewardUsedUpdate))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
