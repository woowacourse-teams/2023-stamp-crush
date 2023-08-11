package com.stampcrush.backend.fixture;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.Owner;

import java.time.LocalTime;

public final class CafeFixture {

    public static final Cafe GITCHAN_CAFE = new Cafe(
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

    public static Cafe cafeOfSavedOwner(Owner savedOwner) {
        return new Cafe(
                "깃짱카페",
                LocalTime.NOON,
                LocalTime.MIDNIGHT,
                "01012345678",
                "#",
                "안녕하세요",
                "서울시 올림픽로 어쩌고",
                "루터회관",
                "10-222-333",
                savedOwner
        );
    }

    private CafeFixture() {
    }
}
