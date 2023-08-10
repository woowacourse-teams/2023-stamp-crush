package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.manager.coupon.response.CafeCustomerFindResponse;
import com.stampcrush.backend.api.manager.coupon.response.CustomerAccumulatingCouponFindResponse;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponFindStep.고객의_쿠폰_조회하고_결과_반환;
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
    private RegisterCustomerRepository registerCustomerRepository;

    @Test
    void 쿠폰을_발급한다() {
        // given
        Owner owner = 사장_생성();
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        RegisterCustomer savedCustomer = registerCustomerRepository.save(REGISTER_CUSTOMER_YOUNGHO);
        CouponCreateRequest reqeust = new CouponCreateRequest(savedCafeId);

        // when
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(owner, reqeust, savedCustomer.getId());

        List<CustomerAccumulatingCouponFindResponse> coupons = 고객의_쿠폰_조회하고_결과_반환(savedCafeId, savedCustomer);

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
                                        0,
                                        null,
                                        false,
                                        10
                                )
                        )
        );
    }

    @Test
    void 스탬프를_적립한다() {
        // given
        Owner owner = 사장_생성();
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        RegisterCustomer savedCustomer = registerCustomerRepository.save(new RegisterCustomer("name", "phone", "id", "pw"));
        CouponCreateRequest reqeust = new CouponCreateRequest(savedCafeId);

        // when
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(owner, reqeust, savedCustomer.getId());

        StampCreateRequest stampCreateRequest = new StampCreateRequest(4);
        쿠폰에_스탬프를_적립_요청(owner, savedCustomer, couponId, stampCreateRequest);

        List<CustomerAccumulatingCouponFindResponse> coupons = 고객의_쿠폰_조회하고_결과_반환(savedCafeId, savedCustomer);

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
        RegisterCustomer customer1 = registerCustomerRepository.save(new RegisterCustomer("name", "phone", "id", "pw"));
        CouponCreateRequest reqeust1 = new CouponCreateRequest(savedCafeId);
        Long coupon1Id = 쿠폰_생성_요청하고_아이디_반환(owner, reqeust1, customer1.getId());

        RegisterCustomer customer2 = registerCustomerRepository.save(new RegisterCustomer("name2", "phone2", "id2", "pw2"));
        CouponCreateRequest reqeust2 = new CouponCreateRequest(savedCafeId);
        Long coupon2Id = 쿠폰_생성_요청하고_아이디_반환(owner, reqeust2, customer2.getId());

        StampCreateRequest coupon1StampCreateRequest = new StampCreateRequest(5);
        쿠폰에_스탬프를_적립_요청(owner, customer1, coupon1Id, coupon1StampCreateRequest);

        StampCreateRequest coupon2StampCreateRequest = new StampCreateRequest(21);
        쿠폰에_스탬프를_적립_요청(owner, customer2, coupon2Id, coupon2StampCreateRequest);

        // when
        List<CafeCustomerFindResponse> customers = given().log().all()
                .auth().preemptive()
                .basic(owner.getLoginId(), owner.getEncryptedPassword())
                .when()
                .get("/api/admin/cafes/{cafeId}/customers", savedCafeId)
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

        RegisterCustomer customer = registerCustomerRepository.save(new RegisterCustomer("name2", "phone2", "id2", "pw2"));
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(savedCafeId);
        Long oldCouponId = 쿠폰_생성_요청하고_아이디_반환(owner, couponCreateRequest, customer.getId());
        StampCreateRequest oldCouponStampCreate = new StampCreateRequest(3);
        쿠폰에_스탬프를_적립_요청(owner, customer, oldCouponId, oldCouponStampCreate);

        Long newCouponId = 쿠폰_생성_요청하고_아이디_반환(owner, couponCreateRequest, customer.getId());
        StampCreateRequest newCouponStampCreate = new StampCreateRequest(8);
        쿠폰에_스탬프를_적립_요청(owner, customer, newCouponId, newCouponStampCreate);

        // when
        List<CustomerAccumulatingCouponFindResponse> coupons = given().log().all()
                .queryParam("cafe-id", savedCafeId)
                .queryParam("active", true)
                .auth().preemptive()
                .basic(owner.getLoginId(), owner.getEncryptedPassword())
                .when()
                .get("/api/admin/customers/{customerId}/coupons", customer.getId())
                .thenReturn()
                .jsonPath()
                .getList("coupons", CustomerAccumulatingCouponFindResponse.class);

        // then
        CustomerAccumulatingCouponFindResponse expected = new CustomerAccumulatingCouponFindResponse(newCouponId,
                customer.getId(),
                customer.getNickname(),
                newCouponStampCreate.getEarningStampCount(),
                null,
                false,
                10);

        assertThat(coupons).usingRecursiveFieldByFieldElementComparatorIgnoringFields("expireDate")
                .containsExactlyInAnyOrder(expected);
    }

    @Test
    void 특정_카페의_특정_고객의_적립중인_쿠폰_조회_시_적립중인_쿠폰이_없으면_빈_리스트_반환() {
        // given
        Owner owner = 사장_생성();
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);

        RegisterCustomer customer = registerCustomerRepository.save(new RegisterCustomer("name2", "phone2", "id2", "pw2"));
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(savedCafeId);
        Long oldCouponId = 쿠폰_생성_요청하고_아이디_반환(owner, couponCreateRequest, customer.getId());
        StampCreateRequest oldCouponStampCreate = new StampCreateRequest(10); // 리워드 받고 쿠폰 만료됨
        쿠폰에_스탬프를_적립_요청(owner, customer, oldCouponId, oldCouponStampCreate);

        // when
        List<CustomerAccumulatingCouponFindResponse> coupons = given().log().all()
                .queryParam("cafe-id", savedCafeId)
                .queryParam("active", true)
                .auth().preemptive()
                .basic(owner.getLoginId(), owner.getEncryptedPassword())
                .when()
                .get("/api/admin/customers/{customerId}/coupons", customer.getId())
                .thenReturn()
                .jsonPath()
                .getList("coupons", CustomerAccumulatingCouponFindResponse.class);

        // then
        assertThat(coupons).isEmpty();
    }

    public static ExtractableResponse<Response> 쿠폰에_스탬프를_적립_요청(Owner owner, RegisterCustomer customer, Long couponId, StampCreateRequest stampCreateRequest) {
        return given()
                .log().all()
                .body(stampCreateRequest)
                .contentType(JSON)
                .auth().preemptive()
                .basic(owner.getLoginId(), owner.getEncryptedPassword())
                .when()
                .post("/api/admin/customers/{customerId}/coupons/{couponId}/stamps", customer.getId(), couponId)
                .then().log().all()
                .extract();
    }

    private Owner 사장_생성() {
        return ownerRepository.save(new Owner("owner", "id", "pw", "phone"));
    }
}
