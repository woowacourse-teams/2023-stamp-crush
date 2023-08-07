package com.stampcrush.backend.acceptance.coupon;

import com.stampcrush.backend.acceptance.AcceptanceTest;
import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.visitor.coupon.response.CustomerCouponFindResponse;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.stampcrush.backend.acceptance.step.CafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.CafeCreateStep.카페_생성_요청하고_아이디_반환;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class ManagerCouponCommandIntegrationTest extends AcceptanceTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private RegisterCustomerRepository registerCustomerRepository;

    @Test
    void 쿠폰을_발급한다() {
        Owner owner = 사장_생성();
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        RegisterCustomer savedCustomer = registerCustomerRepository.save(new RegisterCustomer("name", "phone", "id", "pw"));
        CouponCreateRequest reqeust = new CouponCreateRequest(savedCafeId);

        RestAssured.given().log().all()
                .contentType(JSON)
                .auth().preemptive()
                .basic(owner.getLoginId(), owner.getEncryptedPassword())
                .body(reqeust)
                .when()
                .post("/api/admin/customers/{customerId}/coupons", savedCustomer.getId())
                .then()
                .log().all();

        List<CustomerCouponFindResponse> coupons = RestAssured.given().log().all()
                .auth().preemptive()
                .basic(savedCustomer.getLoginId(), savedCustomer.getEncryptedPassword())
                .when()
                .get("/api/coupons")
                .thenReturn()
                .jsonPath()
                .getList("coupons", CustomerCouponFindResponse.class);

        assertThat(coupons.size()).isEqualTo(1);
    }

    private Owner 사장_생성() {
        return ownerRepository.save(new Owner("owner", "id", "pw", "phone"));
    }

}
