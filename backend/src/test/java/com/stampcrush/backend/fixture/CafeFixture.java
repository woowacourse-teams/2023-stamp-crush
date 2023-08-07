package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.cafe.Cafe;

import java.time.LocalTime;

public class CafeFixture {

    public static final Cafe GITCHAN_CAFE = new Cafe(
            "깃짱카페",
            LocalTime.NOON,
            LocalTime.MIDNIGHT,
            "01012345678",
            "cafeImageUrl",
            "introduction",
            "roadAddress",
            "detailAddress",
            "businessRegistrationNumber",
            OwnerFixture.GITCHAN
    );
}
