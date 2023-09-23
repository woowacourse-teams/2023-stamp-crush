package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.visitor.profile.response.VisitorProfilesFindByPhoneNumberResponse;
import com.stampcrush.backend.api.visitor.profile.response.VisitorProfilesFindResponse;
import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfileFindByPhoneNumberResultDto;
import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfileFindResultDto;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.CustomerType;
import com.stampcrush.backend.helper.BearerAuthHelper;
import com.stampcrush.backend.repository.user.CustomerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.stampcrush.backend.fixture.CustomerFixture.REGISTER_CUSTOMER_GITCHAN;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class VisitorProfileFindAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 고객의_프로필을_조회한다() {
        // given
        Customer customer = customerRepository.save(REGISTER_CUSTOMER_GITCHAN);

        ExtractableResponse<Response> response = given()
                .log().all()
                .auth().preemptive()
                .oauth2(BearerAuthHelper.generateToken(customer.getId()))

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
        Customer customer = Customer.registeredCustomerBuilder()
                .nickname("jena")
                .build();
        customer.registerLoginId("jenaId");
        customer.registerEncryptedPassword("jenaPw");
        Customer savedCustomer = customerRepository.save(customer);

        ExtractableResponse<Response> response = given()
                .log().all()
                .auth().preemptive()
                .oauth2(BearerAuthHelper.generateToken(customer.getId()))

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
    void 전화번호로_고객의_프로필을_조회한다() {
        // given
        Customer customer = customerRepository.save(REGISTER_CUSTOMER_GITCHAN);

        // when
        ExtractableResponse<Response> response = given()
                .log().all()
                .auth().preemptive()
                .oauth2(BearerAuthHelper.generateToken(customer.getId()))

                .when()
                .get("/api/profiles/search?phone-number=" + customer.getPhoneNumber())

                .then()
                .log().all()
                .extract();

        VisitorProfilesFindByPhoneNumberResponse expected = VisitorProfilesFindByPhoneNumberResponse.from(
                new VisitorProfileFindByPhoneNumberResultDto(customer.getId(),
                        customer.getNickname(), customer.getPhoneNumber(), CustomerType.REGISTER.name().toLowerCase()));
        VisitorProfilesFindByPhoneNumberResponse result = response.body().as(VisitorProfilesFindByPhoneNumberResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(result).usingRecursiveComparison().isEqualTo(expected)
        );
    }

    @Test
    void 없는_전화번호로_고객의_프로필을_조회하면_빈_배열을_반환한다() {
        // given
        Customer customer = customerRepository.save(REGISTER_CUSTOMER_GITCHAN);
        String wrongPhoneNumber = "7777777777";

        // when
        ExtractableResponse<Response> response = given()
                .log().all()
                .auth().preemptive()
                .oauth2(BearerAuthHelper.generateToken(customer.getId()))

                .when()
                .get("/api/profiles/search?phone-number=" + wrongPhoneNumber)

                .then()
                .log().all()
                .extract();

        VisitorProfilesFindByPhoneNumberResponse expected = VisitorProfilesFindByPhoneNumberResponse
                .from(null);

        VisitorProfilesFindByPhoneNumberResponse result = response.body().as(VisitorProfilesFindByPhoneNumberResponse.class);
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(result).usingRecursiveComparison().isEqualTo(expected)
        );
    }
}
