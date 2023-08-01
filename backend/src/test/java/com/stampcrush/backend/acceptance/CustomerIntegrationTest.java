package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.admin.customer.request.TemporaryCustomerCreateRequest;
import com.stampcrush.backend.api.admin.customer.response.CustomerFindResponse;
import com.stampcrush.backend.api.admin.customer.response.CustomersFindResponse;
import com.stampcrush.backend.application.customer.dto.CustomerFindDto;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.stampcrush.backend.fixture.OwnerFixture.OWNER3;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

public class CustomerIntegrationTest extends AcceptanceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = ownerRepository.save(OWNER3);
    }

    @Test
    void 전화번호로_가입_고객을_조회한다() {
        // given
        Customer customer = new RegisterCustomer("제나", "01012345678", "jena", "1234");
        customerRepository.save(customer);

        // when
        ExtractableResponse<Response> response = requestFindCustomerByPhoneNumber(owner, "01012345678");
        CustomersFindResponse customers = response.body().as(CustomersFindResponse.class);

        // then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(OK.value());
            softAssertions.assertThat(customers.getCustomer()).containsExactlyInAnyOrder(CustomerFindResponse.from(CustomerFindDto.from(customer)));
        });
    }

    @Test
    void 전화번호로_임시_고객을_조회한다() {
        // given
        Customer customer = TemporaryCustomer.from("01012345678");
        customerRepository.save(customer);

        // when
        ExtractableResponse<Response> response = requestFindCustomerByPhoneNumber(owner, "01012345678");
        CustomersFindResponse customers = response.body().as(CustomersFindResponse.class);

        // then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(OK.value());
            softAssertions.assertThat(customers.getCustomer()).containsExactly(CustomerFindResponse.from(CustomerFindDto.from(customer)));
        });
    }

    @Test
    void 고객이_존재하지_않는_경우_빈_배열을_반환한다() {
        // given, when
        ExtractableResponse<Response> response = requestFindCustomerByPhoneNumber(owner, "01012345678");
        CustomersFindResponse customers = response.body().as(CustomersFindResponse.class);

        // then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(OK.value());
            softAssertions.assertThat(customers.getCustomer().size()).isEqualTo(0);
        });
    }

    @Test
    void 임시_고객을_생성한다() {
        // given
        TemporaryCustomerCreateRequest temporaryCustomerCreateRequest = new TemporaryCustomerCreateRequest("01012345678");

        // when
        Long temporaryCustomerId = createTemporaryCustomer(owner, temporaryCustomerCreateRequest);
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
        Customer customer = TemporaryCustomer.from("01012345678");
        customerRepository.save(customer);
        TemporaryCustomerCreateRequest temporaryCustomerCreateRequest = new TemporaryCustomerCreateRequest(customer.getPhoneNumber());

        // when, then
        RestAssured.given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(temporaryCustomerCreateRequest)
                .auth().preemptive().basic(owner.getLoginId(), owner.getEncryptedPassword())

                .when()
                .post("/api/admin/temporary-customers")

                .then()
                .statusCode(BAD_REQUEST.value())
                .extract();
    }

    private ExtractableResponse<Response> requestFindCustomerByPhoneNumber(Owner owner, String phoneNumber) {
        return RestAssured.given().
                log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("phone-number", phoneNumber)
                .auth().preemptive().basic(owner.getLoginId(), owner.getEncryptedPassword())

                .when()
                .get("/api/admin/customers")

                .then()
                .log().all()
                .extract();
    }

    private Long createTemporaryCustomer(Owner owner, TemporaryCustomerCreateRequest request) {
        ExtractableResponse<Response> response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .auth().preemptive().basic(owner.getLoginId(), owner.getEncryptedPassword())

                .when()
                .post("/api/admin/temporary-customers")

                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        return getIdFromCreatedResponse(response);
    }

    private long getIdFromCreatedResponse(ExtractableResponse<Response> response) {
        return Long.parseLong(response.header("Location").split("/")[2]);
    }
}
