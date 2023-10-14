package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.customer.request.TemporaryCustomerCreateRequest;
import com.stampcrush.backend.api.visitor.profile.request.VisitorProfilesLinkDataRequest;
import com.stampcrush.backend.entity.user.Customer;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.OWNER_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.임시_고객_회원_가입_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.VisitorLinkDataStep.회원_데이터_연동_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class VisitorLinkDataAcceptanceTest extends AcceptanceTest {

    @Test
    void 기존_임시회원의_데이터와_연동한다() {
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);

        TemporaryCustomerCreateRequest temporaryCustomerCreateRequest = new TemporaryCustomerCreateRequest("01023422455");
        Long temporaryCustomerId = 임시_고객_회원_가입_요청하고_아이디_반환(ownerAccessToken, temporaryCustomerCreateRequest);
        Customer temporaryCustomer = customerRepository.findById(temporaryCustomerId).get();

        String accessToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST);
        Long registerCustomerId = authTokensGenerator.extractMemberId(accessToken);

        ExtractableResponse<Response> response = 회원_데이터_연동_요청(accessToken, new VisitorProfilesLinkDataRequest(temporaryCustomer.getId()));

        Customer linkedCustomer = customerRepository.findById(temporaryCustomerId).get();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(linkedCustomer.getNickname()).isEqualTo(REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST.getNickname()),
                () -> assertThat(linkedCustomer.getOAuthProvider()).isEqualTo(REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST.getoAuthProvider()),
                () -> assertThat(linkedCustomer.getOAuthId()).isEqualTo(REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST.getoAuthId()),
                () -> assertThat(customerRepository.findById(registerCustomerId)).isEmpty()
        );
    }
}
