package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.cafe.response.CafeCouponSettingFindResponse;
import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.fixture.CustomerFixture;
import com.stampcrush.backend.fixture.OwnerFixture;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCouponSettingFindStep.카페의_현재_쿠폰_디자인_정책_조회_요청;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCouponSettingFindStep.쿠폰이_발급될때의_쿠폰_디자인_조회_요청;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ManagerCafeCouponSettingFindAcceptanceTest extends AcceptanceTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 카페_사장은_쿠폰이_발급되었을_때_쿠폰_디자인_조회_가능하다() {
        // given
        Owner owner = ownerRepository.save(OwnerFixture.JENA);
        Customer customer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_JENA);

        Long cafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);


        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(cafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(owner, couponCreateRequest, customer.getId());


        // when
        ExtractableResponse<Response> response = 쿠폰이_발급될때의_쿠폰_디자인_조회_요청(owner, cafeId, couponId);
        CafeCouponSettingFindResponse cafeCouponSettingFindResponse = response.body().as(CafeCouponSettingFindResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    void 내_카페에서_발급한_쿠폰이_아니면_쿠폰이_발급되었을때의_디자인_조회_불가능하다() {
        // given
        Owner owner = ownerRepository.save(OwnerFixture.JENA);
        Owner notOwner = ownerRepository.save(new Owner("notowner", "id", "pw", "01012349876"));

        Customer customer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_JENA);

        Long cafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);


        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(cafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(owner, couponCreateRequest, customer.getId());

        // when
        ExtractableResponse<Response> response = 쿠폰이_발급될때의_쿠폰_디자인_조회_요청(notOwner, cafeId, couponId);

        // then
        assertThat(response.statusCode()).isEqualTo(401);
    }

    @Test
    void 카페_사장은_카페의_현재_쿠폰_디자인_정책_조회_가능하다() {
        // given
        Owner owner = ownerRepository.save(OwnerFixture.JENA);
        Customer customer = customerRepository.save(CustomerFixture.REGISTER_CUSTOMER_JENA);

        Long cafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);

        // when
        ExtractableResponse<Response> response = 카페의_현재_쿠폰_디자인_정책_조회_요청(owner, cafeId);
        CafeCouponSettingFindResponse cafeCouponSettingFindResponse = response.body().as(CafeCouponSettingFindResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);

    }

    @Test
    void 내카페가_아닌_카페의_현재_쿠폰_디자인_정책_조회_불가능하다() {
        // given
        Owner owner = ownerRepository.save(OwnerFixture.JENA);
        Owner notOwner = ownerRepository.save(new Owner("notowner", "id", "pw", "01012349876"));

        Long cafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);

        // when
        ExtractableResponse<Response> response = 카페의_현재_쿠폰_디자인_정책_조회_요청(notOwner, cafeId);

        // then
        assertThat(response.statusCode()).isEqualTo(401);
    }
}
