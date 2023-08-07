package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.cafe.Cafe;

import java.time.LocalTime;

public final class CafeFixture {

    public static final Cafe GITCHAN_CAFE_SAVED = new Cafe(
            "깃짱카페",
            LocalTime.NOON,
            LocalTime.MIDNIGHT,
            "01012345678",
            "cafeImageUrl",
            "introduction",
            "roadAddress",
            "detailAddress",
            "buisnessRegistrationNumber",
            OwnerFixture.GITCHAN
    );
}
