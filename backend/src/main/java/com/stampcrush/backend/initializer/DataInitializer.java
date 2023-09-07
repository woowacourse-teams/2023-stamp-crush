package com.stampcrush.backend.initializer;

import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.cafe.CafeCouponDesign;
import com.stampcrush.backend.entity.cafe.CafePolicy;
import com.stampcrush.backend.entity.cafe.CafeStampCoordinate;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.cafe.CafeStampCoordinateRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.time.LocalTime;

@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final CafeRepository cafeRepository;
    private final OwnerRepository ownerRepository;
    private final CafeStampCoordinateRepository cafeStampCoordinateRepository;
    private final CafePolicyRepository cafePolicyRepository;
    private final CafeCouponDesignRepository cafeCouponDesignRepository;
    private final CustomerRepository customerRepository;

    public void run(ApplicationArguments args) {

        Owner owner_1 = ownerRepository.save(new Owner("stampcrush_1", "id", "1234", "01011111111"));
        Owner owner_2 = ownerRepository.save(new Owner("stampcrush_2", "id", "1234", "01011111111"));
        Cafe cafe = cafeRepository.save(new Cafe(
                "스탬프크러쉬카페",
                LocalTime.of(12, 30),
                LocalTime.of(18, 30),
                "0211111111",
                "http://www.cafeImage.com",
                "안녕하세요",
                "잠실동12길",
                "14층",
                "11111111",
                owner_1));

        Customer registeredCustomer1 = Customer.registeredCustomerBuilder()
                .nickname("레오")
                .email("01038626099")
                .loginId("leoId")
                .encryptedPassword("leoPw")
                .oAuthProvider(OAuthProvider.KAKAO)
                .oAuthId(123L)
                .build();

        Customer registeredCustomer2 = Customer.registeredCustomerBuilder()
                .nickname("하디")
                .email("01064394814")
                .loginId("hardy")
                .encryptedPassword("hardyPw")
                .oAuthProvider(OAuthProvider.KAKAO)
                .oAuthId(123L)
                .build();

        Customer temporaryCustomer = Customer.temporaryCustomerBuilder()
                .phoneNumber("01012345678")
                .build();


        Customer savedRegisteredCustomer1 = customerRepository.save(registeredCustomer1);
        Customer savedRegisteredCustomer2 = customerRepository.save(registeredCustomer2);
        Customer savedTemporaryCustomer = customerRepository.save(temporaryCustomer);

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
