package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.visitor.profile.response.VisitorProfilesFindResponse;
import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfileFindResultDto;
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
        // given
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

        // when
        VisitorProfilesFindResponse expected = VisitorProfilesFindResponse.from(
                new VisitorProfileFindResultDto(customer.getId(),
                        customer.getNickname(), customer.getPhoneNumber(), customer.getEmail()));
        VisitorProfilesFindResponse result = response.body().as(VisitorProfilesFindResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(result).usingRecursiveComparison().isEqualTo(expected)
        );
    }

    @Test
    void 고객의_전화번호가_등록되어있지_않을때_전화번호_필드가_null이다() {
        // given
        RegisterCustomer customer = RegisterCustomer.builder().nickname("jena").build();
        customer.registerLoginId("jenaId");
        customer.registerEncryptedPassword("jenaPw");
        RegisterCustomer savedCustomer = registerCustomerRepository.save(customer);

        ExtractableResponse<Response> response = given()
                .log().all()
                .auth().preemptive()
                .basic(customer.getLoginId(), customer.getEncryptedPassword())

                .when()
                .get("/api/profiles")

                .then()
                .log().all()
                .extract();

        // when
        VisitorProfilesFindResponse expected = VisitorProfilesFindResponse.from(
                new VisitorProfileFindResultDto(customer.getId(),
                        customer.getNickname(), customer.getPhoneNumber(), customer.getEmail()));
        VisitorProfilesFindResponse result = response.body().as(VisitorProfilesFindResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(result).usingRecursiveComparison().isEqualTo(expected)
        );
    }
}
