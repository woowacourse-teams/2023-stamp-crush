package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.auth.repository.BlackListRepository;
import com.stampcrush.backend.common.DataCleaner;
import com.stampcrush.backend.common.DataClearExtension;
import com.stampcrush.backend.common.KorNamingConverter;
import com.stampcrush.backend.repository.cafe.CafeCouponDesignRepository;
import com.stampcrush.backend.repository.cafe.CafePolicyRepository;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@KorNamingConverter
@ExtendWith(DataClearExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AcceptanceTest {

    @Autowired
    protected AuthTokensGenerator authTokensGenerator;

    @Autowired
    protected BlackListRepository blackListRepository;

    @Autowired
    protected CafeRepository cafeRepository;

    @Autowired
    protected CafeCouponDesignRepository cafeCouponDesignRepository;

    @Autowired
    protected CafePolicyRepository cafePolicyRepository;

    @Autowired
    protected CustomerRepository customerRepository;

    @Autowired
    protected OwnerRepository ownerRepository;

    @Autowired
    protected CouponRepository couponRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private DataCleaner cleaner;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        cleaner.clear();
    }
}
