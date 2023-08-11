package com.stampcrush.backend.application.visitor.reward;

import com.stampcrush.backend.application.ServiceSliceTest;
import com.stampcrush.backend.application.visitor.reward.dto.VisitorRewardsFindResultDto;
import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.reward.Reward;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.exception.CustomerNotFoundException;
import com.stampcrush.backend.fixture.CustomerFixture;
import com.stampcrush.backend.fixture.RewardFixture;
import com.stampcrush.backend.repository.reward.RewardRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ServiceSliceTest
class VisitorRewardsFindServiceTest {

    @InjectMocks
    private VisitorRewardsFindService visitorRewardsFindService;

    @Mock
    private RewardRepository rewardRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void 리워드를_조회할_때_해당_고객이_존재하지_않으면_예외처리한다() {
        given(customerRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> visitorRewardsFindService.findRewards(1L, true))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void 리워드를_조회한다() {
        // given
        RegisterCustomer customer = CustomerFixture.REGISTER_CUSTOMER_GITCHAN_SAVED;
        Reward availableReward = RewardFixture.REWARD_USED_TRUE;
        Reward usedReward = RewardFixture.REWARD_USED_FALSE;

        given(customerRepository.findById(anyLong()))
                .willReturn(Optional.of(customer));
        given(rewardRepository.findAllByCustomerAndUsed(any(RegisterCustomer.class), eq(true)))
                .willReturn(List.of(availableReward));
        given(rewardRepository.findAllByCustomerAndUsed(any(RegisterCustomer.class), eq(false)))
                .willReturn(List.of(usedReward));

        // when
        List<VisitorRewardsFindResultDto> availableRewards = visitorRewardsFindService.findRewards(1L, false);
        List<VisitorRewardsFindResultDto> usedRewards = visitorRewardsFindService.findRewards(1L, true);

        // then
        assertAll(
                () -> assertThat(availableRewards)
                        .usingRecursiveComparison()
                        .isEqualTo(
                                List.of(
                                        VisitorRewardsFindResultDto.from(availableReward)
                                )
                        ),
                () -> assertThat(usedRewards)
                        .usingRecursiveComparison()
                        .isEqualTo(
                                List.of(
                                        VisitorRewardsFindResultDto.from(usedReward)
                                )
                        )
        );
    }
}
