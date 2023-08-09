package com.stampcrush.backend.acceptance.coupon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stampcrush.backend.acceptance.AcceptanceTest;
import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.manager.coupon.response.CafeCustomerFindResponse;
import com.stampcrush.backend.api.manager.coupon.response.CouponCreateResponse;
import com.stampcrush.backend.api.manager.coupon.response.CustomerAccumulatingCouponFindResponse;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.stampcrush.backend.acceptance.step.CafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.CafeCreateStep.카페_생성_요청하고_아이디_반환;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class ManagerCouponCommandIntegrationTest extends AcceptanceTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private RegisterCustomerRepository registerCustomerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 쿠폰을_발급한다() {
        // given
        Owner owner = 사장_생성();
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        RegisterCustomer savedCustomer = registerCustomerRepository.save(new RegisterCustomer("name", "phone", "id", "pw"));
        CouponCreateRequest reqeust = new CouponCreateRequest(savedCafeId);

        // when
        Long couponId = 쿠폰_생성_후_아이디_반환(owner, savedCustomer, reqeust);

        List<CustomerAccumulatingCouponFindResponse> coupons = RestAssured.given().log().all()
                .auth().preemptive()
                .basic(savedCustomer.getLoginId(), savedCustomer.getEncryptedPassword())
                .queryParam("cafe-id", savedCafeId)
                .queryParam("active", true)
                .when()
                .get("api/admin/customers/{customerId}/coupons", savedCustomer.getId())
                .thenReturn()
                .jsonPath()
                .getList("coupons", CustomerAccumulatingCouponFindResponse.class);

        CustomerAccumulatingCouponFindResponse expected = new CustomerAccumulatingCouponFindResponse(
                couponId,
                savedCustomer.getId(),
                savedCustomer.getNickname(),
                0,
                null,
                false,
                10
        );

        // then
        assertAll(
                () -> assertThat(coupons.size()).isEqualTo(1),
                () -> assertThat(coupons).usingRecursiveFieldByFieldElementComparatorIgnoringFields("expireDate")
                        .containsExactlyInAnyOrder(expected)
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
        Long couponId = 쿠폰_생성_후_아이디_반환(owner, savedCustomer, reqeust);

        StampCreateRequest stampCreateRequest = new StampCreateRequest(4);
        스탬프를_적립한다(owner, savedCustomer, couponId, stampCreateRequest);

        List<CustomerAccumulatingCouponFindResponse> coupons = RestAssured.given().log().all()
                .auth().preemptive()
                .basic(savedCustomer.getLoginId(), savedCustomer.getEncryptedPassword())
                .queryParam("cafe-id", savedCafeId)
                .queryParam("active", true)
                .when()
                .get("api/admin/customers/{customerId}/coupons", savedCustomer.getId())
                .thenReturn()
                .jsonPath()
                .getList("coupons", CustomerAccumulatingCouponFindResponse.class);

        CustomerAccumulatingCouponFindResponse expected = new CustomerAccumulatingCouponFindResponse(
                couponId,
                savedCustomer.getId(),
                savedCustomer.getNickname(),
                stampCreateRequest.getEarningStampCount(),
                null,
                false,
                10
        );

        // then
        assertAll(
                () -> assertThat(coupons.size()).isEqualTo(1),
                () -> assertThat(coupons).usingRecursiveFieldByFieldElementComparatorIgnoringFields("expireDate")
                        .containsExactlyInAnyOrder(expected)
        );
    }

    @Test
    void 사장님_인증_정보_없이_쿠폰을_생성하려고_하면_예외발생() {
        // given
        Owner owner = 사장_생성();
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        RegisterCustomer savedCustomer = registerCustomerRepository.save(new RegisterCustomer("name", "phone", "id", "pw"));
        CouponCreateRequest reqeust = new CouponCreateRequest(savedCafeId);

        // when
        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .contentType(JSON)
                .body(reqeust)
                .when()
                .post("/api/admin/customers/{customerId}/coupons", savedCustomer.getId())
                .then()
                .log().all()
                .extract();

        // then
        int status = extract.statusCode();
        assertThat(status).isEqualTo(UNAUTHORIZED.value());
    }

    @Test
    void 특정_카페의_방문한_고객들의_정보를_조회한다() {
        // given
        Owner owner = 사장_생성();
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        RegisterCustomer customer1 = registerCustomerRepository.save(new RegisterCustomer("name", "phone", "id", "pw"));
        CouponCreateRequest reqeust1 = new CouponCreateRequest(savedCafeId);
        Long coupon1Id = 쿠폰_생성_후_아이디_반환(owner, customer1, reqeust1);

        RegisterCustomer customer2 = registerCustomerRepository.save(new RegisterCustomer("name2", "phone2", "id2", "pw2"));
        CouponCreateRequest reqeust2 = new CouponCreateRequest(savedCafeId);
        Long coupon2Id = 쿠폰_생성_후_아이디_반환(owner, customer2, reqeust2);

        StampCreateRequest coupon1StampCreateRequest = new StampCreateRequest(5);
        스탬프를_적립한다(owner, customer1, coupon1Id, coupon1StampCreateRequest);

        StampCreateRequest coupon2StampCreateRequest = new StampCreateRequest(21);
        스탬프를_적립한다(owner, customer2, coupon2Id, coupon2StampCreateRequest);

        // when
        List<CafeCustomerFindResponse> customers = RestAssured.given().log().all()
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

    private static void 스탬프를_적립한다(Owner owner, RegisterCustomer customer1, Long coupon1Id, StampCreateRequest coupon1StampCreateRequest) {
        RestAssured.given()
                .log().all()
                .body(coupon1StampCreateRequest)
                .contentType(JSON)
                .auth().preemptive()
                .basic(owner.getLoginId(), owner.getEncryptedPassword())
                .when()
                .post("/api/admin/customers/{customerId}/coupons/{couponId}/stamps", customer1.getId(), coupon1Id)
                .then().log().all();
    }

    private Long 쿠폰_생성_후_아이디_반환(Owner owner, RegisterCustomer savedCustomer, CouponCreateRequest reqeust) {
        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .contentType(JSON)
                .auth().preemptive()
                .basic(owner.getLoginId(), owner.getEncryptedPassword())
                .body(reqeust)
                .when()
                .post("/api/admin/customers/{customerId}/coupons", savedCustomer.getId())
                .then()
                .log().all()
                .extract();

        CouponCreateResponse couponCreateResponse = extract.body().as(CouponCreateResponse.class);
        return couponCreateResponse.getCouponId();
    }

    private Owner 사장_생성() {
        return ownerRepository.save(new Owner("owner", "id", "pw", "phone"));
    }

}
