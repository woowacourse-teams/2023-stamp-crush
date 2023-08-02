package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.coupon.Coupon;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.coupon.CouponRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.stampcrush.backend.acceptance.step.CafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.CafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.CouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.CouponFindStep.고객의_쿠폰_카페별로_1개씩_조회_요청;
import static com.stampcrush.backend.fixture.OwnerFixture.GITCHAN;
import static com.stampcrush.backend.fixture.OwnerFixture.JENA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CustomerCouponFindAcceptanceTest extends AcceptanceTest {

    private static final RegisterCustomer REGISTER_CUSTOMER = new RegisterCustomer("깃짱", "01012345678", "customer1", "customer1");

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Disabled
    @Test
    void 카페당_하나의_쿠폰을_조회할_수_있다() {
        // given

        // TODO: Owner에 대한 회원가입 로직이 생기면 요청으로 대체한다.
        Owner gitchan = ownerRepository.save(GITCHAN);
        Owner jena = ownerRepository.save(JENA);

        RegisterCustomer customer = customerRepository.save(REGISTER_CUSTOMER);

        Long gitchanCafeId = 카페_생성_요청하고_아이디_반환(gitchan, gitchan.getId(), CAFE_CREATE_REQUEST);
        Cafe gitchanCafe = cafeRepository.findById(gitchanCafeId).get();

        Long jenaCafeId = 카페_생성_요청하고_아이디_반환(jena, jena.getId(), CAFE_CREATE_REQUEST);
        Cafe jenaCafe = cafeRepository.findById(jenaCafeId).get();

        Long gitchanCafeCouponId = 쿠폰_생성_요청하고_아이디_반환(gitchan, new CouponCreateRequest(gitchanCafeId), customer.getId());
        Coupon gitchanCafeCoupon = couponRepository.findById(gitchanCafeCouponId).get();

        Long jenaCafeCouponId = 쿠폰_생성_요청하고_아이디_반환(jena, new CouponCreateRequest(jenaCafeId), customer.getId());
        Coupon jenaCafeCoupon = couponRepository.findById(jenaCafeCouponId).get();

        // when
        ExtractableResponse<Response> response = 고객의_쿠폰_카페별로_1개씩_조회_요청(customer);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("coupons")).isNotEmpty(),
                () -> assertThat(response.jsonPath().getList("coupons").size()).isEqualTo(2),
                () -> assertThat(response.jsonPath().getLong("coupons[0].cafeInfo.id")).isEqualTo(gitchanCafeId),
                () -> assertThat(response.jsonPath().getString("coupons[0].cafeInfo.name")).isEqualTo(gitchanCafe.getName()),
                () -> assertThat(response.jsonPath().getLong("coupons[0].couponInfos[0].id")).isEqualTo(gitchanCafeCouponId),
                () -> assertThat(response.jsonPath().getString("coupons[0].couponInfos[0].status")).isEqualTo(gitchanCafeCoupon.getStatus().name()),
                () -> assertThat(response.jsonPath().getList("coupons[0].couponInfos[0].coordinates")).isNotEmpty(),
                () -> assertThat(response.jsonPath().getLong("coupons[1].cafeInfo.id")).isEqualTo(jenaCafeCouponId),
                () -> assertThat(response.jsonPath().getLong("coupons[1].couponInfos[0].id")).isEqualTo(jenaCafeCouponId)
        );
    }

    @Test
    @Disabled
    void 여러_개의_쿠폰이_있는_경우_ACCUMULATING인_쿠폰만_조회된다() {
        // given

        // TODO: Owner에 대한 회원가입 로직이 생기면 요청으로 대체한다.
        Owner gitchan = ownerRepository.save(GITCHAN);
        Owner jena = ownerRepository.save(JENA);

        RegisterCustomer customer = customerRepository.save(REGISTER_CUSTOMER);

        Long gitchanCafeId = 카페_생성_요청하고_아이디_반환(gitchan, gitchan.getId(), CAFE_CREATE_REQUEST);
        Long jenaCafeId = 카페_생성_요청하고_아이디_반환(jena, jena.getId(), CAFE_CREATE_REQUEST);

        Long gitchanCafeCouponId = 쿠폰_생성_요청하고_아이디_반환(gitchan, new CouponCreateRequest(gitchanCafeId), customer.getId());
        Long jenaCafeCouponId = 쿠폰_생성_요청하고_아이디_반환(jena, new CouponCreateRequest(jenaCafeId), customer.getId());
        accumulateCouponUntilRewarded(gitchanCafeCouponId);

        // when
        ExtractableResponse<Response> response = 고객의_쿠폰_카페별로_1개씩_조회_요청(customer);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("coupons")).isNotEmpty(),
                () -> assertThat(response.jsonPath().getList("coupons").size()).isEqualTo(1),
                () -> assertThat(response.jsonPath().getLong("coupons[0].cafeInfo.id")).isEqualTo(jenaCafeId),
                () -> assertThat(response.jsonPath().getLong("coupons[0].couponInfos[0].id")).isEqualTo(jenaCafeCouponId)
        );
    }

    private void accumulateCouponUntilRewarded(Long couponId) {
        Coupon gitchanCafeCoupon = couponRepository.findById(couponId).get();
        int couponMaxStampCount = gitchanCafeCoupon.getCouponMaxStampCount();
        for (int i = 0; i < couponMaxStampCount; i++) {
            gitchanCafeCoupon.accumulate(1);
        }
    }
}
