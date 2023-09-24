package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.visitor.profile.request.VisitorProfilesPhoneNumberUpdateRequest;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import com.stampcrush.backend.entity.user.Customer;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorProfilesCommandStep.고객의_전화번호_등록_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

class VisitorProfilesCommandAcceptanceTest extends AcceptanceTest {

    private static final Customer OAUTH_REGISTER_CUSTOMER = Customer.registeredCustomerBuilder()
            .nickname("깃짱")
            .email("gitchan@naver.com")
            .oAuthId(1L)
            .oAuthProvider(OAuthProvider.KAKAO)
            .loginId(null)
            .encryptedPassword(null)
            .build();

    @Autowired
    private EntityManager entityManager;

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

        ExtractableResponse<Response> response = 고객의_전화번호_등록_요청(accessToken, new VisitorProfilesPhoneNumberUpdateRequest("01012345678"));

        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

//    @Test
//    @Disabled
//        // TODO: 상황 연출이 어려움....!
//    void 중복되는_전화번호로_요청하면_예외가_발생한다() {
//        EntityTransaction transaction = entityManager.getTransaction();
//
//        transaction.begin();
//        Customer recentCustomer = customerRepository.save(REGISTER_CUSTOMER_GITCHAN);
//        transaction.commit();
//
//        transaction.begin();
//        Customer newOAuthRegisterCustomer = OAUTH_REGISTER_CUSTOMER;
//        newOAuthRegisterCustomer.registerLoginId("loginId");
//        newOAuthRegisterCustomer.registerEncryptedPassword("password");
//        Customer newCustomer = customerRepository.save(newOAuthRegisterCustomer);
//        transaction.commit();
//
//        ExtractableResponse<Response> response = 고객의_전화번호_등록_요청(newCustomer, new VisitorProfilesPhoneNumberUpdateRequest(recentCustomer.getPhoneNumber()));
//
//        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
//    }
}
