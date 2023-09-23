package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.customer.request.TemporaryCustomerCreateRequest;
import com.stampcrush.backend.api.manager.customer.response.CustomerFindResponse;
import com.stampcrush.backend.api.manager.customer.response.CustomersFindResponse;
import com.stampcrush.backend.application.manager.customer.dto.CustomerFindDto;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.stampcrush.backend.acceptance.step.ManagerCustomerFindStep.전화번호로_고객_조회_요청;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.OWNER_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.임시_고객_회원_가입_요청하고_아이디_반환;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpStatus.OK;

class ManagerCustomerFindAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 전화번호로_가입_고객을_조회한다() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);

        Long temporaryCustomerId = 임시_고객_회원_가입_요청하고_아이디_반환(ownerAccessToken, new TemporaryCustomerCreateRequest("01012345678"));
        Customer customer = customerRepository.findById(temporaryCustomerId).get();

        // when
        ExtractableResponse<Response> response = 전화번호로_고객_조회_요청(ownerAccessToken, "01012345678");
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
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);

        Long temporaryCustomerId = 임시_고객_회원_가입_요청하고_아이디_반환(ownerAccessToken, new TemporaryCustomerCreateRequest("01012345678"));
        Customer customer = customerRepository.findById(temporaryCustomerId).get();

        // when
        ExtractableResponse<Response> response = 전화번호로_고객_조회_요청(ownerAccessToken, "01012345678");
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
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);

        ExtractableResponse<Response> response = 전화번호로_고객_조회_요청(ownerAccessToken, "01012345678");
        CustomersFindResponse customers = response.body().as(CustomersFindResponse.class);

        // then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(OK.value());
            softAssertions.assertThat(customers.getCustomer().size()).isEqualTo(0);
        });
    }
}
