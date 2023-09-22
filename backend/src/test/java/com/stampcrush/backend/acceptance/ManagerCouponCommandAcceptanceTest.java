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
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.helper.BearerAuthHelper;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
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
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.ManagerStampCreateStep.쿠폰에_스탬프를_적립_요청;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.fixture.CustomerFixture.REGISTER_CUSTOMER_YOUNGHO;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class ManagerCouponCommandAcceptanceTest extends AcceptanceTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthTokensGenerator authTokensGenerator;

    private static Response 고객_조회_요청(Owner owner, Long savedCafeId) {
        return given()
                .log().all()
                .auth().preemptive()
                .oauth2(BearerAuthHelper.generateToken(owner.getId()))

                .when()
                .get("/api/admin/cafes/{cafeId}/customers", savedCafeId);
    }

    @Test
    void 쿠폰을_발급한다() {
        // given
        String managerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterOwnerCreateRequest("leo", OAuthProvider.KAKAO, 123L));
        Long managerId = authTokensGenerator.extractMemberId(managerToken);
        Owner owner = ownerRepository.findById(managerId).get();

        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        String customerToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterCustomerCreateRequest("customer", "email", OAuthProvider.KAKAO, 123L));
        Long customerId = authTokensGenerator.extractMemberId(customerToken);
        Customer customer = customerRepository.findById(customerId).get();

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);

        // when
        System.out.println("실제 비즈니스 로직");
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(owner, request, customerId);

        // then
        System.out.println("쿠폰 발급 결과 조회");
        List<CustomerAccumulatingCouponFindResponse> coupons = 고객의_쿠폰_조회하고_결과_반환(owner, savedCafeId, customer);

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
        Owner owner = ownerRepository.save(new Owner("owner", "id1", "pw1", "01029384234"));
        Owner notOwner = ownerRepository.save(new Owner("notowner", "id2", "pw2", "01049384234"));

        Customer savedCustomer = customerRepository.save(REGISTER_CUSTOMER_YOUNGHO);

        Long cafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        CouponCreateRequest request = new CouponCreateRequest(cafeId);

        // when
        ExtractableResponse<Response> response = 쿠폰_생성_요청(notOwner, request, savedCustomer.getId());

        // then
        assertThat(response.statusCode()).isEqualTo(401);
    }

    @Test
    void 스탬프를_적립한다() {
        // given
        String managerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterOwnerCreateRequest("leo", OAuthProvider.KAKAO, 123L));
        Long managerId = authTokensGenerator.extractMemberId(managerToken);
        Owner owner = ownerRepository.findById(managerId).get();
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        Customer savedCustomer = customerRepository.save(
                Customer.registeredCustomerBuilder()
                        .nickname("name")
                        .phoneNumber("phone")
                        .loginId("id")
                        .encryptedPassword("pw")
                        .build()
        );

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);

        Long couponId = 쿠폰_생성_요청하고_아이디_반환(owner, request, savedCustomer.getId());

        // when
        System.out.println("실제 비즈니스 로직 시작");
        StampCreateRequest stampCreateRequest = new StampCreateRequest(4);
        쿠폰에_스탬프를_적립_요청(owner, savedCustomer, couponId, stampCreateRequest);

        // then
        System.out.println("적립 여부 확인을 위한 api 호출");
        List<CustomerAccumulatingCouponFindResponse> coupons = 고객의_쿠폰_조회하고_결과_반환(owner, savedCafeId, savedCustomer);

        // then
        assertAll(
                () -> assertThat(coupons.size()).isEqualTo(1),
                () -> assertThat(coupons)
                        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("expireDate")
                        .containsExactlyInAnyOrder(
                                new CustomerAccumulatingCouponFindResponse(
                                        couponId,
                                        savedCustomer.getId(),
                                        savedCustomer.getNickname(),
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
        CouponCreateRequest reqeust = new CouponCreateRequest(1L);
        // when
        ExtractableResponse<Response> response = given()
                .log().all()
                .contentType(JSON)
                .body(reqeust)

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
        Owner owner = 사장_생성();
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        Customer customer1 = customerRepository.save(
                Customer.registeredCustomerBuilder()
                        .nickname("name")
                        .phoneNumber("phone")
                        .loginId("id")
                        .encryptedPassword("pw")
                        .build()
        );
        CouponCreateRequest reqeust1 = new CouponCreateRequest(savedCafeId);
        Long coupon1Id = 쿠폰_생성_요청하고_아이디_반환(owner, reqeust1, customer1.getId());
        Customer customer2 = customerRepository.save(
                Customer.registeredCustomerBuilder()
                        .nickname("name2")
                        .phoneNumber("phone2")
                        .loginId("id2")
                        .encryptedPassword("pw2")
                        .build()
        );
        CouponCreateRequest reqeust2 = new CouponCreateRequest(savedCafeId);
        Long coupon2Id = 쿠폰_생성_요청하고_아이디_반환(owner, reqeust2, customer2.getId());

        StampCreateRequest coupon1StampCreateRequest = new StampCreateRequest(5);
        쿠폰에_스탬프를_적립_요청(owner, customer1, coupon1Id, coupon1StampCreateRequest);

        StampCreateRequest coupon2StampCreateRequest = new StampCreateRequest(21);
        쿠폰에_스탬프를_적립_요청(owner, customer2, coupon2Id, coupon2StampCreateRequest);

        // when
        List<CafeCustomerFindResponse> customers = 고객_조회_요청(owner, savedCafeId)

                .thenReturn()
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
        Owner owner = 사장_생성();
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);

        Customer customer = customerRepository.save(
                Customer.registeredCustomerBuilder()
                        .nickname("name2")
                        .phoneNumber("phone2")
                        .loginId("id2")
                        .encryptedPassword("pw2")
                        .build()
        );
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(savedCafeId);
        Long oldCouponId = 쿠폰_생성_요청하고_아이디_반환(owner, couponCreateRequest, customer.getId());
        StampCreateRequest oldCouponStampCreate = new StampCreateRequest(3);
        쿠폰에_스탬프를_적립_요청(owner, customer, oldCouponId, oldCouponStampCreate);

        Long newCouponId = 쿠폰_생성_요청하고_아이디_반환(owner, couponCreateRequest, customer.getId());
        StampCreateRequest newCouponStampCreate = new StampCreateRequest(8);
        쿠폰에_스탬프를_적립_요청(owner, customer, newCouponId, newCouponStampCreate);

        // when
        ExtractableResponse<Response> response = 고객의_쿠폰_조회_요청(savedCafeId, owner, customer);
        CustomerAccumulatingCouponsFindResponse coupons = response.body().as(CustomerAccumulatingCouponsFindResponse.class);
        // then
        CustomerAccumulatingCouponFindResponse expected = new CustomerAccumulatingCouponFindResponse(newCouponId,
                customer.getId(),
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
        Owner owner = 사장_생성();
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);

        Customer customer = customerRepository.save(Customer.registeredCustomerBuilder()
                .nickname("name2")
                .phoneNumber("phone2")
                .loginId("id2")
                .encryptedPassword("pw2")
                .build());
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(savedCafeId);
        Long oldCouponId = 쿠폰_생성_요청하고_아이디_반환(owner, couponCreateRequest, customer.getId());
        StampCreateRequest oldCouponStampCreate = new StampCreateRequest(10); // 리워드 받고 쿠폰 만료됨
        쿠폰에_스탬프를_적립_요청(owner, customer, oldCouponId, oldCouponStampCreate);

        // when
        List<CustomerAccumulatingCouponFindResponse> coupons = 고객의_쿠폰_조회하고_결과_반환(owner, savedCafeId, customer);

        // then
        assertThat(coupons).isEmpty();
    }

    @Test
    void 카페_정책을_바꾸고_현재_적립_중인_쿠폰이_이전_정책의_쿠폰일때_isPrevious_true_확인() {
        // given
        Owner owner = 사장_생성();
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);

        Customer customer = customerRepository.save(Customer.registeredCustomerBuilder()
                .nickname("name2")
                .phoneNumber("phone2")
                .loginId("id2")
                .encryptedPassword("pw2")
                .build());
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(savedCafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(owner, couponCreateRequest, customer.getId());
        StampCreateRequest stampCreateRequest = new StampCreateRequest(3);
        쿠폰에_스탬프를_적립_요청(owner, customer, couponId, stampCreateRequest);

        CafeCouponSettingUpdateRequest updateRequest = CAFE_COUPON_SETTING_UPDATE_REQUEST;
        카페_쿠폰_정책_수정_요청(updateRequest, owner, savedCafeId);

        // when
        ExtractableResponse<Response> response = 고객의_쿠폰_조회_요청(savedCafeId, owner, customer);
        CustomerAccumulatingCouponsFindResponse coupons = response.body().as(CustomerAccumulatingCouponsFindResponse.class);

        // then
        CustomerAccumulatingCouponFindResponse expected = new CustomerAccumulatingCouponFindResponse(couponId,
                customer.getId(),
                customer.getNickname(),
                stampCreateRequest.getEarningStampCount(),
                null,
                true,
                10);

        assertThat(coupons.getCoupons()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("expireDate")
                .containsExactlyInAnyOrder(expected);
    }

    private Owner 사장_생성() {
        return ownerRepository.save(new Owner("owner", "id", "pw", "phone"));
    }
}
