package com.stampcrush.backend.application.manager.reward;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.application.manager.reward.dto.RewardFindDto;
import com.stampcrush.backend.application.manager.reward.dto.RewardFindResultDto;
import com.stampcrush.backend.application.manager.reward.dto.RewardUsedUpdateDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.reward.Reward;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.reward.RewardRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@ServiceSliceTest
class ManagerRewardCommandServiceTest {

    @Autowired
    private ManagerRewardCommandService managerRewardCommandService;

    @Autowired
    private ManagerRewardFindService managerRewardFindService;

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Owner owner_1;
    private Owner owner_2;
    private Cafe cafe_1;
    private Cafe cafe_2;
    private Customer registerCustomer_1;
    private Customer registerCustomer_2;
    private Customer temporaryCustomer;
    private Reward unusedReward;

    @BeforeEach
    void setUp() {
        owner_1 = ownerRepository.save(new Owner("lisa", "lisa@naver.com", "1234", "01011111111"));
        owner_2 = ownerRepository.save(new Owner("tommy", "tommy@naver.com", "5678", "01099999999"));

        cafe_1 = cafeRepository.save(new Cafe("stamp-crush", LocalTime.of(18, 0), LocalTime.of(23, 59), "0211111111", "imageUrl", "안녕하세요", "잠실도로명", "14층", "11-11111", owner_1));
        cafe_2 = cafeRepository.save(new Cafe("wrongCafe", LocalTime.of(18, 0), LocalTime.of(23, 59), "0211111111", "imageUrl", "안녕하세요", "잠실도로", "1층", "11-11111", owner_2));

        Customer registered1 = Customer.registeredCustomerBuilder()
                .nickname("registered")
                .phoneNumber("01022222222")
                .email("ehdgur@naver.com")
                .build();

        Customer registered2 = Customer.registeredCustomerBuilder()
                .nickname("registered2")
                .phoneNumber("01044444444")
                .email("dsadsa@naver.com")
                .build();

        registerCustomer_1 = customerRepository.save(registered1);
        registerCustomer_2 = customerRepository.save(registered2);
        temporaryCustomer = customerRepository.save(Customer.temporaryCustomerBuilder()
                .phoneNumber("01033333333")
                .build());

        unusedReward = rewardRepository.save(new Reward("Americano", registerCustomer_1, cafe_1));
    }

    @Test
    void 리워드를_사용한다() {
        // given
        RewardUsedUpdateDto rewardUsedUpdateDto = new RewardUsedUpdateDto(unusedReward.getId(), registerCustomer_1.getId(), cafe_1.getId(), true);

        // when
        managerRewardCommandService.useReward(owner_1.getId(), rewardUsedUpdateDto);

        // then
        assertThat(unusedReward.getUsed()).isTrue();
    }

    @Test
    void 존재하지_않는_리워드를_사용하려하면_예외를_던진다() {
        // given
        RewardUsedUpdateDto rewardUsedUpdateDto = new RewardUsedUpdateDto(unusedReward.getId(), Long.MAX_VALUE, cafe_1.getId(), true);

        // when, then
        assertThatThrownBy(() -> managerRewardCommandService.useReward(owner_1.getId(), rewardUsedUpdateDto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 존재하지_않는_고객에_대한_리워드를_사용하려하면_예외를_던진다() {
        // given
        RewardUsedUpdateDto rewardUsedUpdateDto = new RewardUsedUpdateDto(Long.MAX_VALUE, registerCustomer_1.getId(), cafe_1.getId(), true);

        // when, then
        assertThatThrownBy(() -> managerRewardCommandService.useReward(owner_1.getId(), rewardUsedUpdateDto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 존재하지_않는_카페에서_리워드를_사용하려하면_예외를_던진다() {
        // given
        RewardUsedUpdateDto rewardUsedUpdateDto = new RewardUsedUpdateDto(unusedReward.getId(), registerCustomer_1.getId(), Long.MAX_VALUE, true);

        // when, then
        assertThatThrownBy(() -> managerRewardCommandService.useReward(owner_1.getId(), rewardUsedUpdateDto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 임시회원이_리워드를_사용하려하면_예외를_던진다() {
        //given
        RewardUsedUpdateDto rewardUsedUpdateDto = new RewardUsedUpdateDto(unusedReward.getId(), temporaryCustomer.getId(), cafe_1.getId(), true);

        // when, then
        assertThatThrownBy(() -> managerRewardCommandService.useReward(owner_1.getId(), rewardUsedUpdateDto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이미_사용된_리워드를_사용하려하면_예외를_던진다() {
        //given
        RewardUsedUpdateDto rewardUsedUpdateDto = new RewardUsedUpdateDto(unusedReward.getId(), registerCustomer_1.getId(), cafe_1.getId(), true);
        managerRewardCommandService.useReward(owner_1.getId(), rewardUsedUpdateDto);

        // when, then
        assertThatThrownBy(() -> managerRewardCommandService.useReward(owner_1.getId(), rewardUsedUpdateDto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 다른_카페에의해_생성된_리워드를_사용하려하면_예외를_던진다() {
        //given
        RewardUsedUpdateDto rewardUsedUpdateDto = new RewardUsedUpdateDto(unusedReward.getId(), registerCustomer_1.getId(), cafe_2.getId(), true);

        // when, then
        assertThatThrownBy(() -> managerRewardCommandService.useReward(owner_2.getId(), rewardUsedUpdateDto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 소유자가_아닌_고객이_리워드를_사용하려하면_예외를_던진다() {
        //given
        RewardUsedUpdateDto rewardUsedUpdateDto = new RewardUsedUpdateDto(unusedReward.getId(), registerCustomer_2.getId(), cafe_1.getId(), true);

        // when, then
        assertThatThrownBy(() -> managerRewardCommandService.useReward(owner_1.getId(), rewardUsedUpdateDto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 사용된_리워드_목록을_조회한다() {
        // given
        RewardFindDto rewardFindDto = new RewardFindDto(registerCustomer_1.getId(), cafe_1.getId(), true);

        // when
        List<RewardFindResultDto> rewards = managerRewardFindService.findRewards(cafe_1.getOwner().getId(), rewardFindDto);

        // then
        assertThat(rewards).hasSize(0);
    }

    @Test
    void 미사용_리워드_목록을_조회한다() {
        // given
        RewardFindDto rewardFindDto = new RewardFindDto(registerCustomer_1.getId(), cafe_1.getId(), false);

        // when
        List<RewardFindResultDto> rewards = managerRewardFindService.findRewards(cafe_1.getOwner().getId(), rewardFindDto);

        // then
        assertThat(rewards).hasSize(1);
    }
}
