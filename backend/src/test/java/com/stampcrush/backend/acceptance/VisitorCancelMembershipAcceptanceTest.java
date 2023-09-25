package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.repository.user.CustomerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorCancelMembershipStep.가입_고객_회원_탈퇴_요청;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.NO_CONTENT;

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
                () -> assertEquals(response.statusCode(), NO_CONTENT.value()),
                () -> assertThat(findCustomer).isEmpty()
        );
    }

    @Test
    void 이것저것_많이_한_회원도_탈퇴할_수_있다() {
        String ownerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterOwnerCreateRequest("leo", OAuthProvider.KAKAO, 123L));
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(ownerToken, CAFE_CREATE_REQUEST);

        String customerToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterCustomerCreateRequest("깃장의테스트용회원", "email", OAuthProvider.KAKAO, 123L));
        Long customerId = authTokensGenerator.extractMemberId(customerToken);

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);

        Long couponId = 쿠폰_생성_요청하고_아이디_반환(ownerToken, request, customerId);

        ExtractableResponse<Response> response = 가입_고객_회원_탈퇴_요청(customerToken);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(NO_CONTENT.value())
        );
    }
}
