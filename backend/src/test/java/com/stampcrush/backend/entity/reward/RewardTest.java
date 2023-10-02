package com.stampcrush.backend.entity.reward;

import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@KorNamingConverter
@DataJpaTest
public class RewardTest {

    private Cafe cafe_1;
    private Cafe cafe_2;
    private Customer registerCustomer1;
    private Customer registerCustomer2;
    private Customer temporaryCustomer;

    @BeforeEach
    void setUp() {
        Owner owner_1 = new Owner("lisa", "lisa@naver.com", "1234", "01011111111");
        Owner owner_2 = new Owner("tommy", "tommy@naver.com", "5678", "01099999999");
        cafe_1 = new Cafe("stamp-crush", LocalTime.of(18, 0), LocalTime.of(23, 59), "0211111111", "imageUrl", "안녕하세요", "잠실도로명", "14층", "11-11111", owner_1);
        cafe_2 = new Cafe("wrongCafe", LocalTime.of(18, 0), LocalTime.of(23, 59), "0211111111", "imageUrl", "안녕하세요", "잠실도로", "1층", "11-11111", owner_2);

        registerCustomer1 = Customer.registeredCustomerBuilder()
                .nickname("registered")
                .phoneNumber("01022222222")
                .build();
        registerCustomer2 = Customer.registeredCustomerBuilder()
                .nickname("registered2")
                .phoneNumber("01044444444")
                .build();
        temporaryCustomer = Customer.temporaryCustomerBuilder()
                .phoneNumber("01033333333")
                .build();
    }

    @Test
    void 가입회원이_리워드를_사용한다() {
        // given
        Reward reward = new Reward("Americano", registerCustomer1, cafe_1);

        // when
        reward.useReward(registerCustomer1, cafe_1);

        // then
        assertThat(reward.getUsed()).isTrue();
    }

    @Test
    void 임시회원이_리워드를_사용한다() {
        //given
        Reward reward = new Reward("Americano", temporaryCustomer, cafe_1);

        // when
        reward.useReward(temporaryCustomer, cafe_1);

        // then
        assertThat(reward.getUsed()).isTrue();
    }

    @Test
    void 이미_사용된_리워드를_사용려하면_예외를_던진다() {
        // given
        Reward reward = new Reward("Americano", registerCustomer1, cafe_1);
        reward.useReward(registerCustomer1, cafe_1);

        // when, then
        assertThatThrownBy(() -> reward.useReward(registerCustomer1, cafe_1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 다른_카페에의해_생성된_리워드를_사용하려하면_예외를_던진다() {
        // given
        Reward reward = new Reward("Americano", registerCustomer1, cafe_1);

        // when, then
        assertThatThrownBy(() -> reward.useReward(registerCustomer1, cafe_2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 소유자가_아닌_고객이_리워드를_사용하려하면_예외를_던진다() {
        // given
        Reward reward = new Reward("Americano", registerCustomer1, cafe_1);

        // when, then
        assertThatThrownBy(() -> reward.useReward(registerCustomer2, cafe_1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
