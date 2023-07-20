package com.stampcrush.backend.initializer;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.cafe.CafeStampCoordinate;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.cafe.CafeStampCoordinateRepository;
import com.stampcrush.backend.repository.coupon.CouponDesignRepository;
import com.stampcrush.backend.repository.coupon.CouponPolicyRepository;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import com.stampcrush.backend.repository.user.TemporaryCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Order(2)
public class DataInitializer implements ApplicationRunner {

    private final CouponRepository couponRepository;
    private final TemporaryCustomerRepository temporaryCustomerRepository;
    private final RegisterCustomerRepository registerCustomerRepository;
    private final CafeRepository cafeRepository;
    private final CouponDesignRepository couponDesignRepository;
    private final CouponPolicyRepository couponPolicyRepository;
    private final OwnerRepository ownerRepository;
    private final CafeStampCoordinateRepository cafeStampCoordinateRepository;
    private final CafePolicyRepository cafePolicyRepository;
    private final CafeCouponDesignRepository cafeCouponDesignRepository;

    public void run(ApplicationArguments args) throws Exception {

        Owner owner_1 = ownerRepository.save(new Owner("stampcrush_1", "id", "1234", "01011111111"));
        Owner owner_2 = ownerRepository.save(new Owner("stampcrush_2", "id", "1234", "01011111111"));
        Cafe cafe = cafeRepository.save(new Cafe(
                "스탬프크러쉬카페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "잠실동12길",
                "14층",
                "11111111",
                owner_1));

        RegisterCustomer customer1 = registerCustomerRepository.save(new RegisterCustomer("레오", "01038626099", "leo", "1234"));
        RegisterCustomer customer2 = registerCustomerRepository.save(new RegisterCustomer("하디", "01064394814", "hardy", "5678"));
        TemporaryCustomer temporaryCustomer = temporaryCustomerRepository.save(new TemporaryCustomer("yunsaeng", "01012345678"));

        CafePolicy savedCafePolicy = cafePolicyRepository.save(
                new CafePolicy(
                        10,
                        "아메리카노",
                        12,
                        false,
                        cafe
                )
        );

        CafeCouponDesign savedCafeCouponDesign = cafeCouponDesignRepository.save(
                new CafeCouponDesign(
                        "#",
                        "#",
                        "#",
                        false,
                        cafe
                )
        );

        CafeStampCoordinate savedCafeStampCoordinate1 = cafeStampCoordinateRepository.save(
                new CafeStampCoordinate(
                        1, 1, 1, savedCafeCouponDesign
                )
        );

        CafeStampCoordinate savedCafeStampCoordinate2 = cafeStampCoordinateRepository.save(
                new CafeStampCoordinate(
                        2, 2, 1, savedCafeCouponDesign
                )
        );

        CafeStampCoordinate savedCafeStampCoordinate3 = cafeStampCoordinateRepository.save(
                new CafeStampCoordinate(
                        3, 3, 1, savedCafeCouponDesign
                )
        );

        CafeStampCoordinate savedCafeStampCoordinate4 = cafeStampCoordinateRepository.save(
                new CafeStampCoordinate(
                        4, 4, 1, savedCafeCouponDesign
                )
        );

        CafeStampCoordinate savedCafeStampCoordinate5 = cafeStampCoordinateRepository.save(
                new CafeStampCoordinate(
                        5, 5, 1, savedCafeCouponDesign
                )
        );

        CafeStampCoordinate savedCafeStampCoordinate6 = cafeStampCoordinateRepository.save(
                new CafeStampCoordinate(
                        6, 6, 1, savedCafeCouponDesign
                )
        );

        CafeStampCoordinate savedCafeStampCoordinate7 = cafeStampCoordinateRepository.save(
                new CafeStampCoordinate(
                        7, 7, 1, savedCafeCouponDesign
                )
        );

        CafeStampCoordinate savedCafeStampCoordinate8 = cafeStampCoordinateRepository.save(
                new CafeStampCoordinate(
                        8, 8, 1, savedCafeCouponDesign
                )
        );

        CafeStampCoordinate savedCafeStampCoordinate9 = cafeStampCoordinateRepository.save(
                new CafeStampCoordinate(
                        9, 9, 1, savedCafeCouponDesign
                )
        );

        CafeStampCoordinate savedCafeStampCoordinate10 = cafeStampCoordinateRepository.save(
                new CafeStampCoordinate(
                        10, 10, 1, savedCafeCouponDesign
                )
        );
    }
}
