package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static com.stampcrush.backend.acceptance.step.VisitorCancelMembershipStep.가입_고객_회원_탈퇴_요청;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitorCancelMembershipAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthTokensGenerator authTokensGenerator;

    @Test
    void 회원_탈퇴할_수_있다() {
        String accessToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST);
        ExtractableResponse<Response> response = 가입_고객_회원_탈퇴_요청(accessToken);

        Long customerId = authTokensGenerator.extractMemberId(accessToken);
        Optional<Customer> findCustomer = customerRepository.findById(customerId);

        assertAll(
                () -> assertEquals(response.statusCode(), HttpStatus.NO_CONTENT.value()),
                () -> assertThat(findCustomer).isEmpty()
        );
    }
}
