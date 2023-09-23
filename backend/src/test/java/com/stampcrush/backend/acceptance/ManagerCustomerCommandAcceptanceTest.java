package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.customer.request.TemporaryCustomerCreateRequest;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.OWNER_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

class ManagerCustomerCommandAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 임시_고객을_생성한다() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        TemporaryCustomerCreateRequest temporaryCustomerCreateRequest = TEMPORARY_CUSTOMER_CREATE_REQUEST;
        Long temporaryCustomerId = 임시_고객_회원_가입_요청하고_아이디_반환(ownerAccessToken, temporaryCustomerCreateRequest);
        Customer temporaryCustomer = customerRepository.findById(temporaryCustomerId).get();

        // when
        String phoneNumber = temporaryCustomerCreateRequest.getPhoneNumber();
        String lastFourDigits = phoneNumber.substring(phoneNumber.length() - 4);

        // then
        assertSoftly(softly -> {
            softly.assertThat(temporaryCustomer.getNickname()).isEqualTo(lastFourDigits);
            softly.assertThat(temporaryCustomer.getPhoneNumber()).isEqualTo(phoneNumber);
        });
    }

    @Test
    void 존재하는_회원의_번호로_고객을_생성하려면_에러를_발생한다() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);

        Long temporaryCustomerId = 임시_고객_회원_가입_요청하고_아이디_반환(ownerAccessToken, TEMPORARY_CUSTOMER_CREATE_REQUEST);
        Customer temporaryCustomer = customerRepository.findById(temporaryCustomerId).get();

        ExtractableResponse<Response> response = 임시_고객_회원_가입_요청(
                ownerAccessToken,
                new TemporaryCustomerCreateRequest(temporaryCustomer.getPhoneNumber())
        );

        // when, then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
    }
}
