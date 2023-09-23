package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.cafe.request.CafeCouponSettingUpdateRequest;
import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.manager.coupon.response.CafeCustomerFindResponse;
import com.stampcrush.backend.api.manager.coupon.response.CustomerAccumulatingCouponFindResponse;
import com.stampcrush.backend.api.manager.coupon.response.CustomerAccumulatingCouponsFindResponse;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCouponSettingUpdateStep.CAFE_COUPON_SETTING_UPDATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCouponSettingUpdateStep.카페_쿠폰_정책_수정_요청;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponFindStep.고객의_쿠폰_조회_요청;
import static com.stampcrush.backend.acceptance.step.ManagerCouponFindStep.고객의_쿠폰_조회하고_결과_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCustomerFindStep.고객_목록_조회_요청;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.*;
import static com.stampcrush.backend.acceptance.step.ManagerStampCreateStep.쿠폰에_스탬프를_적립_요청;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

class ManagerCouponCommandAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthTokensGenerator authTokensGenerator;

    @Test
    void 쿠폰을_발급한다() {
        // given
        String ownerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterOwnerCreateRequest("leo", OAuthProvider.KAKAO, 123L));

        Long savedCafeId = 카페_생성_요청하고_아이디_반환(ownerToken, CAFE_CREATE_REQUEST);
        String customerToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterCustomerCreateRequest("customer", "email", OAuthProvider.KAKAO, 123L));
        Long customerId = authTokensGenerator.extractMemberId(customerToken);
        Customer customer = customerRepository.findById(customerId).get();

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);

        // when
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(ownerToken, request, customerId);

        // then
        List<CustomerAccumulatingCouponFindResponse> coupons = 고객의_쿠폰_조회하고_결과_반환(ownerToken, savedCafeId, customerId);

        // then
        assertAll(
                () -> assertThat(coupons.size()).isEqualTo(1),
                () -> assertThat(coupons)
                        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("expireDate")
                        .containsExactlyInAnyOrder(
                                new CustomerAccumulatingCouponFindResponse(
                                        couponId,
                                        customerId,
                                        customer.getNickname(),
                                        0,
                                        null,
                                        false,
                                        10
                                )
                        )
        );
    }

    @Test
    void 내카페가_아닌_카페의_쿠폰을_발급할수_없다() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        String notOwnerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST_2);

        String customerToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterCustomerCreateRequest("customer", "email", OAuthProvider.KAKAO, 123L));
        Long customerId = authTokensGenerator.extractMemberId(customerToken);

        Long cafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, CAFE_CREATE_REQUEST);
        CouponCreateRequest request = new CouponCreateRequest(cafeId);

        // when
        ExtractableResponse<Response> response = 쿠폰_생성_요청(notOwnerAccessToken, request, customerId);

        // then
        assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
    }

    @Test
    void 스탬프를_적립한다() {
        // given
        String ownerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterOwnerCreateRequest("leo", OAuthProvider.KAKAO, 123L));

        Long savedCafeId = 카페_생성_요청하고_아이디_반환(ownerToken, CAFE_CREATE_REQUEST);
        String customerToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterCustomerCreateRequest("customer", "email", OAuthProvider.KAKAO, 123L));
        Long customerId = authTokensGenerator.extractMemberId(customerToken);
        Customer customer = customerRepository.findById(customerId).get();

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);

        Long couponId = 쿠폰_생성_요청하고_아이디_반환(ownerToken, request, customerId);

        // when
        StampCreateRequest stampCreateRequest = new StampCreateRequest(4);
        쿠폰에_스탬프를_적립_요청(ownerToken, customerId, couponId, stampCreateRequest);

        // then
        List<CustomerAccumulatingCouponFindResponse> coupons = 고객의_쿠폰_조회하고_결과_반환(ownerToken, savedCafeId, customerId);

        // then
        assertAll(
                () -> assertThat(coupons.size()).isEqualTo(1),
                () -> assertThat(coupons)
                        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("expireDate")
                        .containsExactlyInAnyOrder(
                                new CustomerAccumulatingCouponFindResponse(
                                        couponId,
                                        customerId,
                                        customer.getNickname(),
                                        stampCreateRequest.getEarningStampCount(),
                                        null,
                                        false,
                                        10
                                )
                        )
        );
    }

    @Test
    void 사장님_인증_정보_없이_쿠폰을_생성하려고_하면_예외발생() {
        // given
        CouponCreateRequest request = new CouponCreateRequest(1L);
        // when
        ExtractableResponse<Response> response = given()
                .log().all()
                .contentType(JSON)
                .body(request)

                .when()
                .post("/api/admin/customers/{customerId}/coupons", 1)

                .then()
                .log().all()
                .extract();
        // then
        int status = response.statusCode();

        assertThat(status).isEqualTo(UNAUTHORIZED.value());
    }

    @Test
    void 사장님_인증_정보_없이_스탬프_적립_하려고_하면_예외발생() {
        // given
        StampCreateRequest request = new StampCreateRequest(4);

        // when
        ExtractableResponse<Response> response = given()
                .log().all()
                .body(request)
                .contentType(JSON)

                .when()
                .post("/api/admin/customers/{customerId}/coupons/{couponId}/stamps", 1, 1)

                .then().log().all()
                .extract();
        // then
        int status = response.statusCode();
        assertThat(status).isEqualTo(UNAUTHORIZED.value());
    }

    @Test
    void 특정_카페의_방문한_고객들의_정보를_조회한다() {
        // given
        String ownerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterOwnerCreateRequest("leo", OAuthProvider.KAKAO, 123L));

        Long savedCafeId = 카페_생성_요청하고_아이디_반환(ownerToken, CAFE_CREATE_REQUEST);

        String customerToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterCustomerCreateRequest("customer", "email", OAuthProvider.KAKAO, 123L));
        Long customerId = authTokensGenerator.extractMemberId(customerToken);
        Customer customer1 = customerRepository.findById(customerId).get();

        CouponCreateRequest request1 = new CouponCreateRequest(savedCafeId);

        Long coupon1Id = 쿠폰_생성_요청하고_아이디_반환(ownerToken, request1, customerId);


        String customerToken2 = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterCustomerCreateRequest("customer2", "email2", OAuthProvider.KAKAO, 1293L));
        Long customerId2 = authTokensGenerator.extractMemberId(customerToken2);
        Customer customer2 = customerRepository.findById(customerId2).get();

        CouponCreateRequest request2 = new CouponCreateRequest(savedCafeId);
        Long coupon2Id = 쿠폰_생성_요청하고_아이디_반환(ownerToken, request2, customerId2);

        StampCreateRequest coupon1StampCreateRequest = new StampCreateRequest(5);
        쿠폰에_스탬프를_적립_요청(ownerToken, customerId, coupon1Id, coupon1StampCreateRequest);

        StampCreateRequest coupon2StampCreateRequest = new StampCreateRequest(21);
        쿠폰에_스탬프를_적립_요청(ownerToken, customerId2, coupon2Id, coupon2StampCreateRequest);

        // when
        List<CafeCustomerFindResponse> customers = 고객_목록_조회_요청(ownerToken, savedCafeId)
                .jsonPath()
                .getList("customers", CafeCustomerFindResponse.class);

        // then
        CafeCustomerFindResponse customer1Expected = new CafeCustomerFindResponse(
                coupon1Id,
                customer1.getNickname(),
                coupon1StampCreateRequest.getEarningStampCount(),
                0,
                1,
                10,
                null,
                true);

        CafeCustomerFindResponse customer2Expected = new CafeCustomerFindResponse(
                coupon2Id,
                customer2.getNickname(),
                coupon2StampCreateRequest.getEarningStampCount() % 10,
                coupon2StampCreateRequest.getEarningStampCount() / 10,
                1,
                10,
                null,
                true);

        assertThat(customers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("visitCount", "firstVisitDate")
                .containsExactlyInAnyOrder(customer1Expected, customer2Expected);
    }

    @Test
    void 인증정보_없이_특정_카페의_방문한_고객들의_정보를_조회하면_예외발생() {
        // when
        ExtractableResponse<Response> extract = given()
                .log().all()

                .when()
                .get("/api/admin/cafes/{cafeId}/customers", 1)

                .then()
                .log().all()
                .extract();

        // then
        int statusCode = extract.statusCode();
        assertThat(statusCode).isEqualTo(UNAUTHORIZED.value());
    }

    @Test
    void 특정_고객의_특정_카페에_적립중인_쿠폰정보를_조회한다() {
        // given
        String ownerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterOwnerCreateRequest("leo", OAuthProvider.KAKAO, 123L));
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(ownerToken, CAFE_CREATE_REQUEST);

        String customerToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterCustomerCreateRequest("customer", "email", OAuthProvider.KAKAO, 123L));
        Long customerId = authTokensGenerator.extractMemberId(customerToken);
        Customer customer = customerRepository.findById(customerId).get();

        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(savedCafeId);
        Long oldCouponId = 쿠폰_생성_요청하고_아이디_반환(ownerToken, couponCreateRequest, customerId);
        StampCreateRequest oldCouponStampCreate = new StampCreateRequest(3);
        쿠폰에_스탬프를_적립_요청(ownerToken, customerId, oldCouponId, oldCouponStampCreate);

        Long newCouponId = 쿠폰_생성_요청하고_아이디_반환(ownerToken, couponCreateRequest, customerId);
        StampCreateRequest newCouponStampCreate = new StampCreateRequest(8);
        쿠폰에_스탬프를_적립_요청(ownerToken, customerId, newCouponId, newCouponStampCreate);

        // when
        ExtractableResponse<Response> response = 고객의_쿠폰_조회_요청(savedCafeId, ownerToken, customerId);
        CustomerAccumulatingCouponsFindResponse coupons = response.body().as(CustomerAccumulatingCouponsFindResponse.class);
        // then
        CustomerAccumulatingCouponFindResponse expected = new CustomerAccumulatingCouponFindResponse(newCouponId,
                customerId,
                customer.getNickname(),
                newCouponStampCreate.getEarningStampCount(),
                null,
                false,
                10);

        assertThat(coupons.getCoupons()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("expireDate")
                .containsExactlyInAnyOrder(expected);
    }

    @Test
    void 특정_카페의_특정_고객의_적립중인_쿠폰_조회_시_적립중인_쿠폰이_없으면_빈_리스트_반환() {
        // given
        String ownerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterOwnerCreateRequest("leo", OAuthProvider.KAKAO, 123L));
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(ownerToken, CAFE_CREATE_REQUEST);

        String customerToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterCustomerCreateRequest("customer", "email", OAuthProvider.KAKAO, 123L));
        Long customerId = authTokensGenerator.extractMemberId(customerToken);

        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(savedCafeId);
        Long oldCouponId = 쿠폰_생성_요청하고_아이디_반환(ownerToken, couponCreateRequest, customerId);
        StampCreateRequest oldCouponStampCreate = new StampCreateRequest(10); // 리워드 받고 쿠폰 만료됨
        쿠폰에_스탬프를_적립_요청(ownerToken, customerId, oldCouponId, oldCouponStampCreate);

        // when
        List<CustomerAccumulatingCouponFindResponse> coupons = 고객의_쿠폰_조회하고_결과_반환(ownerToken, savedCafeId, customerId);

        // then
        assertThat(coupons).isEmpty();
    }

    @Test
    void 카페_정책을_바꾸고_현재_적립_중인_쿠폰이_이전_정책의_쿠폰일때_isPrevious_true_확인() {
        // given
        String ownerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterOwnerCreateRequest("leo", OAuthProvider.KAKAO, 123L));
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(ownerToken, CAFE_CREATE_REQUEST);

        String customerToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterCustomerCreateRequest("customer", "email", OAuthProvider.KAKAO, 123L));
        Long customerId = authTokensGenerator.extractMemberId(customerToken);
        Customer customer = customerRepository.findById(customerId).get();

        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(savedCafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(ownerToken, couponCreateRequest, customerId);
        StampCreateRequest stampCreateRequest = new StampCreateRequest(3);
        쿠폰에_스탬프를_적립_요청(ownerToken, customerId, couponId, stampCreateRequest);

        CafeCouponSettingUpdateRequest updateRequest = CAFE_COUPON_SETTING_UPDATE_REQUEST;
        카페_쿠폰_정책_수정_요청(updateRequest, ownerToken, savedCafeId);

        // when
        ExtractableResponse<Response> response = 고객의_쿠폰_조회_요청(savedCafeId, ownerToken, customerId);
        CustomerAccumulatingCouponsFindResponse coupons = response.body().as(CustomerAccumulatingCouponsFindResponse.class);

        // then
        CustomerAccumulatingCouponFindResponse expected = new CustomerAccumulatingCouponFindResponse(couponId,
                customerId,
                customer.getNickname(),
                stampCreateRequest.getEarningStampCount(),
                null,
                true,
                10);

        assertThat(coupons.getCoupons()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("expireDate")
                .containsExactlyInAnyOrder(expected);
    }
}
