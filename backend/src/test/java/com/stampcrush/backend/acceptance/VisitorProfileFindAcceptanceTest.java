package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.stampcrush.backend.fixture.CustomerFixture.REGISTER_CUSTOMER_GITCHAN;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class VisitorProfileFindAcceptanceTest extends AcceptanceTest {

    @Autowired
    private RegisterCustomerRepository registerCustomerRepository;

    @Test
    void 고객의_프로필을_조회한다() {
        RegisterCustomer customer = registerCustomerRepository.save(REGISTER_CUSTOMER_GITCHAN);

        ExtractableResponse<Response> response = given()
                .log().all()
                .auth().preemptive()
                .basic(customer.getLoginId(), customer.getEncryptedPassword())

                .when()
                .get("/api/profiles")

                .then()
                .log().all()
                .extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
//                () -> assertThat(response.jsonPath().getList("profileResponses")).isNotEmpty()
        );
    }
}
