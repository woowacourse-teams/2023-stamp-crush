package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.service.CustomerResponse;
import com.stampcrush.backend.service.CustomersResponse;
import com.stampcrush.backend.service.TemporaryCustomerRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class CustomerIntegrationTest extends IntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 전화번호로_가입_고객을_조회한다() {
        // given
        Customer customer = new RegisterCustomer("제나", "01012345678", "jena", "1234");
        customerRepository.save(customer);

        // when
        ExtractableResponse<Response> response = requestFindCustomerByPhoneNumber("01012345678");
        CustomersResponse customers = response.body().as(CustomersResponse.class);

        // then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(customers.getCustomer()).containsExactly(CustomerResponse.from(customer));
        });

        customerRepository.deleteAll();
    }

    @Test
    void 전화번호로_임시_고객을_조회한다() {
        // given
        Customer customer = new TemporaryCustomer("제나임시", "01012345678");
        customerRepository.save(customer);

        // when
        ExtractableResponse<Response> response = requestFindCustomerByPhoneNumber("01012345678");
        CustomersResponse customers = response.body().as(CustomersResponse.class);

        // then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(customers.getCustomer()).containsExactly(CustomerResponse.from(customer));
        });

        customerRepository.deleteAll();
    }

    @Test
    void 고객이_존재하지_않는_경우_빈_배열을_반환한다() {
        // given, when
        ExtractableResponse<Response> response = requestFindCustomerByPhoneNumber("01012345678");
        CustomersResponse customers = response.body().as(CustomersResponse.class);

        // then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softAssertions.assertThat(customers.getCustomer().size()).isEqualTo(0);
        });

        customerRepository.deleteAll();
    }

    @Test
    void 임시_고객을_생성한다() {
        // given
        TemporaryCustomerRequest temporaryCustomerRequest = new TemporaryCustomerRequest("01012345678");

        // when
        Long temporaryCustomerId = createTemporaryCustomer(temporaryCustomerRequest);
        Customer temporaryCustomer = customerRepository.findById(temporaryCustomerId).get();

        // then
        assertSoftly(softly -> {
            softly.assertThat(temporaryCustomer.getNickname()).isEqualTo("5678");
            softly.assertThat(temporaryCustomer.getPhoneNumber()).isEqualTo("01012345678");
        });
    }

    @Test
    void 존재하는_회원의_번호로_고객을_생성하려면_에러를_발생한다() {
        // given
        Customer customer = new TemporaryCustomer("제나임시", "01012345678");
        customerRepository.save(customer);
        TemporaryCustomerRequest temporaryCustomerRequest = new TemporaryCustomerRequest(customer.getPhoneNumber());

        // when, then
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(temporaryCustomerRequest)
                .when()
                .post("/temporary-customers")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .extract();

        customerRepository.deleteAll();
    }

    private ExtractableResponse<Response> requestFindCustomerByPhoneNumber(String phoneNumber) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("phone-number", phoneNumber)
                .when()
                .get("/customers")
                .then()
                .log().all()
                .extract();
    }

    private Long createTemporaryCustomer(TemporaryCustomerRequest request) {
        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/temporary-customers")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        return getIdFromCreatedResponse(response);
    }

    private long getIdFromCreatedResponse(ExtractableResponse<Response> response) {
        return Long.parseLong(response.header("Location").split("/")[2]);
    }
}
