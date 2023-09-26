package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import com.stampcrush.backend.entity.user.Customer;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCouponSettingUpdateStep.CAFE_COUPON_SETTING_UPDATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCouponSettingUpdateStep.카페_쿠폰_정책_수정_요청;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.ManagerStampCreateStep.쿠폰에_스탬프를_적립_요청;
import static com.stampcrush.backend.acceptance.step.VisitorCancelMembershipStep.가입_고객_회원_탈퇴_요청;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NO_CONTENT;

class VisitorCancelMembershipAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원_탈퇴할_수_있다() {
        String accessToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST);
        ExtractableResponse<Response> response = 가입_고객_회원_탈퇴_요청(accessToken);

        Long customerId = authTokensGenerator.extractMemberId(accessToken);
        Optional<Customer> findCustomer = customerRepository.findById(customerId);

        assertAll(
                () -> assertEquals(NO_CONTENT.value(), response.statusCode()),
                () -> assertThat(findCustomer).isEmpty()
        );
    }

    @Test
    @Disabled
        // TODO: 반드시 통과해야 한다.
    void 잘못된_access_token을_보내는_경우에_회원_탈퇴할_수_없다() {
        String accessToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST);
        String wrongAccessToken = accessToken + "잘못되었지롱";
        ExtractableResponse<Response> response = 가입_고객_회원_탈퇴_요청(wrongAccessToken);

        assertEquals(FORBIDDEN.value(), response.statusCode());
    }

    @Test
    void 이것저것_많이_한_회원도_탈퇴할_수_있다() {
        // given
        String ownerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterOwnerCreateRequest("leo", OAuthProvider.KAKAO, 123L));
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(ownerToken, CAFE_CREATE_REQUEST);

        String customerToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterCustomerCreateRequest("깃장의테스트용회원", "email", OAuthProvider.KAKAO, 123L));
        Long customerId = authTokensGenerator.extractMemberId(customerToken);

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);

        Long couponId = 쿠폰_생성_요청하고_아이디_반환(ownerToken, request, customerId);

        StampCreateRequest stampCreateRequest = new StampCreateRequest(6);
        쿠폰에_스탬프를_적립_요청(ownerToken, customerId, couponId, stampCreateRequest);
        쿠폰에_스탬프를_적립_요청(ownerToken, customerId, couponId, stampCreateRequest);

        카페_쿠폰_정책_수정_요청(CAFE_COUPON_SETTING_UPDATE_REQUEST, ownerToken, savedCafeId);

        Long newCouponId = 쿠폰_생성_요청하고_아이디_반환(ownerToken, request, customerId);

        쿠폰에_스탬프를_적립_요청(ownerToken, customerId, newCouponId, new StampCreateRequest(15));

        // when
        ExtractableResponse<Response> response = 가입_고객_회원_탈퇴_요청(customerToken);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(NO_CONTENT.value())
        );
    }
}
