package com.stampcrush.backend.entity.reward;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class RewardTest {

    private Cafe cafe_1;
    private Cafe cafe_2;
    private Customer registerCustomer_1;
    private Customer registerCustomer_2;
    private Customer temporaryCustomer;

    @BeforeEach
    void setUp() {
        Owner owner_1 = new Owner("lisa", "lisa@naver.com", "1234", "01011111111");
        Owner owner_2 = new Owner("tommy", "tommy@naver.com", "5678", "01099999999");
        cafe_1 = new Cafe("stamp-crush", LocalTime.of(18, 0), LocalTime.of(23, 59), "0211111111", "imageUrl", "잠실도로명", "14층", "11-11111", owner_1);
        cafe_2 = new Cafe("wrongCafe", LocalTime.of(18, 0), LocalTime.of(23, 59), "0211111111", "imageUrl", "잠실도로", "1층", "11-11111", owner_2);
        registerCustomer_1 = new RegisterCustomer("registered", "01022222222", "ehdgur@naver.com", "1111");
        registerCustomer_2 = new RegisterCustomer("registered2", "01044444444", "dsadsa@naver.com", "2345");
        temporaryCustomer = new TemporaryCustomer("temporary", "01033333333");
    }

    @Test
    void 가입회원이_리워드를_사용한다() {
        // given
        Reward reward = new Reward("Americano", false, registerCustomer_1, cafe_1);

        // when
        reward.useReward(registerCustomer_1, cafe_1);

        // then
        assertThat(reward.getUsed()).isTrue();
    }

    @Test
    void 임시회원이_리워드를_사용하려하면_예외를_던진다() {
        //given
        Reward reward = new Reward("Americano", false, temporaryCustomer, cafe_1);

        // when, then
        assertThatThrownBy(() -> reward.useReward(temporaryCustomer, cafe_1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이미_사용된_리워드를_사용려하면_예외를_던진다() {
        // given
        Reward reward = new Reward("Americano", true, registerCustomer_1, cafe_1);

        // when, then
        assertThatThrownBy(() -> reward.useReward(temporaryCustomer, cafe_1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 다른_카페에의해_생성된_리워드를_사용하려하면_예외를_던진다() {
        // given
        Reward reward = new Reward("Americano", true, registerCustomer_1, cafe_1);

        // when, then
        assertThatThrownBy(() -> reward.useReward(registerCustomer_1, cafe_2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 소유자가_아닌_고객이_리워드를_사용하려하면_예외를_던진다() {
        // given
        Reward reward = new Reward("Americano", true, registerCustomer_1, cafe_1);

        // when, then
        assertThatThrownBy(() -> reward.useReward(registerCustomer_2, cafe_1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
