package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.customer.request.TemporaryCustomerCreateRequest;
import com.stampcrush.backend.api.visitor.profile.VisitorProfilesLinkDataRequest;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.OWNER_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.임시_고객_회원_가입_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.VisitorLinkDataStep.회원_데이터_연동_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class VisitorLinkDataAcceptanceTest extends AcceptanceTest {

    private static final OAuthRegisterCustomerCreateRequest O_AUTH_REGISTER_CUSTOMER_CREATE_REQUEST = new OAuthRegisterCustomerCreateRequest(
            "깃짱",
            "gitchan@gmail.com",
            OAuthProvider.KAKAO,
            1235436L
    );

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthTokensGenerator authTokensGenerator;

    @Test
    void 기존_임시회원의_데이터와_연동한다() {
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);

        TemporaryCustomerCreateRequest temporaryCustomerCreateRequest = new TemporaryCustomerCreateRequest("01023422455");
        Long temporaryCustomerId = 임시_고객_회원_가입_요청하고_아이디_반환(ownerAccessToken, temporaryCustomerCreateRequest);
        Customer temporaryCustomer = customerRepository.findById(temporaryCustomerId).get();

        OAuthRegisterCustomerCreateRequest registerCustomerCreateRequest = O_AUTH_REGISTER_CUSTOMER_CREATE_REQUEST;
        String accessToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(registerCustomerCreateRequest);
        Long registerCustomerId = authTokensGenerator.extractMemberId(accessToken);

        ExtractableResponse<Response> response = 회원_데이터_연동_요청(accessToken, new VisitorProfilesLinkDataRequest(temporaryCustomer.getId()));

        Customer linkedCustomer = customerRepository.findById(temporaryCustomerId).get();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(linkedCustomer.getNickname()).isEqualTo(registerCustomerCreateRequest.getNickname()),
                () -> assertThat(linkedCustomer.getOAuthProvider()).isEqualTo(registerCustomerCreateRequest.getoAuthProvider()),
                () -> assertThat(linkedCustomer.getOAuthId()).isEqualTo(registerCustomerCreateRequest.getoAuthId()),
                () -> assertThat(customerRepository.findById(registerCustomerId)).isEmpty()
        );
    }
}
