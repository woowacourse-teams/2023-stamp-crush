package com.stampcrush.backend.api.visitor.reward;

import com.stampcrush.backend.entity.reward.Reward;
import com.stampcrush.backend.fixture.CafeFixture;
import com.stampcrush.backend.fixture.CustomerFixture;

public final class RewardFixture {

    public static final Reward REWARD_USED_FALSE = new Reward(
            1L,
            "깃짱네 아메리카노",
            false,
            CustomerFixture.REGISTER_CUSTOMER_GITCHAN,
            CafeFixture.GITCHAN_CAFE
    );

    public static final Reward REWARD_USED_TRUE = new Reward(
            1L,
            "깃짱네 아메리카노",
            true,
            CustomerFixture.REGISTER_CUSTOMER_GITCHAN,
            CafeFixture.GITCHAN_CAFE
    );
}
