package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.visitor.profile.request.VisitorProfilesPhoneNumberUpdateRequest;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import com.stampcrush.backend.entity.user.Customer;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorProfilesCommandStep.고객의_전화번호_등록_요청_token;
import static org.assertj.core.api.Assertions.assertThat;

public class VisitorProfilesCommandAcceptanceTest extends AcceptanceTest {

    private static final Customer OAUTH_REGISTER_CUSTOMER = Customer.registeredCustomerBuilder()
            .nickname("깃짱")
            .email("gitchan@naver.com")
            .oAuthId(1L)
            .oAuthProvider(OAuthProvider.KAKAO)
            .loginId(null)
            .encryptedPassword(null)
            .build();

    @Test
    void 고객의_전화번호를_저장한다() {
        Customer customer = OAUTH_REGISTER_CUSTOMER;
        OAuthRegisterCustomerCreateRequest request = new OAuthRegisterCustomerCreateRequest(
                customer.getNickname(),
                customer.getEmail(),
                customer.getOAuthProvider(),
                customer.getOAuthId()
        );

        String accessToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(request);

        ExtractableResponse<Response> response = 고객의_전화번호_등록_요청_token(accessToken, new VisitorProfilesPhoneNumberUpdateRequest("01012345678"));

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
