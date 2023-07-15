package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.service.CustomerResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerIntegrationTest extends IntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 전화번호로_가입_고객을_조회한다() {
        // given
        Customer customer = new RegisterCustomer("제나", "01012345678", "jena", "1234");
        customerRepository.save(customer);
//        Customer customer2 = new RegisterCustomer("제나2", "01012345678", "jena2", "12234");
//        customerRepository.save(customer2);

        // when
        ExtractableResponse<Response> response = requestFindCustomerByPhoneNumber("01012345678");
        List<CustomerResponse> customers = response.jsonPath().getList(".", CustomerResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(customers).containsExactly(CustomerResponse.from(customer));

        customerRepository.deleteAll();
    }

    @Test
    void 전화번호로_임시_고객을_조회한다() {
        // given
        Customer customer = new TemporaryCustomer("제나임시", "01012345678");
        customerRepository.save(customer);

        // when
        ExtractableResponse<Response> response = requestFindCustomerByPhoneNumber("01012345678");
        List<CustomerResponse> customers = response.jsonPath().getList(".", CustomerResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(customers).containsExactly(CustomerResponse.from(customer));

        customerRepository.deleteAll();
    }

    @Test
    void 고객이_존재하지_않는_경우_빈_배열을_반환한다() {
        // given, when
        ExtractableResponse<Response> response = requestFindCustomerByPhoneNumber("01012345678");
        List<CustomerResponse> customers = response.jsonPath().getList(".", CustomerResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(customers.size()).isEqualTo(0);

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
}
