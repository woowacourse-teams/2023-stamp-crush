package com.stampcrush.backend.initializer;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.cafe.CafeStampCoordinate;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.cafe.CafeStampCoordinateRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

//@Component
//@Profile("dev")
@RequiredArgsConstructor
public class CouponSettingDataInitializer implements ApplicationRunner {

    private final CafeRepository cafeRepository;
    private final CafePolicyRepository cafePolicyRepository;
    private final CafeCouponDesignRepository cafeCouponDesignRepository;
    private final CafeStampCoordinateRepository cafeStampCoordinateRepository;
    private final OwnerRepository ownerRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Cafe savedCafe = cafeRepository.save(
                new Cafe(
                        "깃짱카페",
                        LocalTime.NOON,
                        LocalTime.MIDNIGHT,
                        "01012345678",
                        "#",
                        "서울시 올림픽로 어쩌고",
                        "루터회관",
                        "10-222-333",
                        ownerRepository.save(new Owner("깃짱", "깃짱아이디", "깃짱비밀번호", "깃짱핸드폰번호"))
                )
        );

        CafePolicy savedCafePolicy = cafePolicyRepository.save(
                new CafePolicy(
                        2,
                        "아메리카노",
                        12,
                        false,
                        savedCafe
                )
        );

        CafeCouponDesign savedCafeCouponDesign = cafeCouponDesignRepository.save(
                new CafeCouponDesign(
                        "#",
                        "#",
                        "#",
                        false,
                        savedCafe
                )
        );

        CafeStampCoordinate savedCafeStampCoordinate1 = cafeStampCoordinateRepository.save(
                new CafeStampCoordinate(
                        1, 1, 1, savedCafeCouponDesign
                )
        );

        CafeStampCoordinate savedCafeStampCoordinate22 = cafeStampCoordinateRepository.save(
                new CafeStampCoordinate(
                        1, 2, 1, savedCafeCouponDesign
                )
        );
    }
}
