package com.stampcrush.backend.repository.coupon;

import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.coupon.CouponStatus;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.fixture.CustomerFixture;
import com.stampcrush.backend.fixture.OwnerFixture;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
class CouponRepositoryTest2 {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 고객의_쿠폰을_상태로_필터링해_조회한다() {
        Owner savedOwner = ownerRepository.save(OwnerFixture.GITCHAN);
        Cafe savedCafe = cafeRepository.save(
                new Cafe(
                        "깃짱카페",
                        "서초구",
                        "어쩌고",
                        "0101010101",
                        savedOwner
                )
        );
        RegisterCustomer savedCustomer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_GITCHAN);

//        new Coupon(LocalDate.MAX, savedCustomer, savedCafe, CouponStatus.ACCUMULATING, Boolean.FALSE, null, null)
    }
}
