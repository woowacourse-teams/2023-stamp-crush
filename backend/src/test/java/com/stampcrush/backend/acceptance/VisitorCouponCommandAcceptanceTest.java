package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.entity.user.Customer;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCouponSettingUpdateStep.CAFE_COUPON_SETTING_UPDATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCouponSettingUpdateStep.카페_쿠폰_정책_수정_요청;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.OWNER_GITCHAN_JOIN_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorCouponDeleteStep.쿠폰_삭제_요청;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.NO_CONTENT;

class VisitorCouponCommandAcceptanceTest extends AcceptanceTest {

    @Test
    void 쿠폰을_삭제할_수_있다() {
        // given, when
        String gitchanAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_GITCHAN_JOIN_REQUEST);
        String customerAccessToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST);
        Long customerId = authTokensGenerator.extractMemberId(customerAccessToken);
        Customer customer = customerRepository.findById(customerId).get();

        Long gitchanCafeId = 카페_생성_요청하고_아이디_반환(gitchanAccessToken, CAFE_CREATE_REQUEST);
        카페_쿠폰_정책_수정_요청(CAFE_COUPON_SETTING_UPDATE_REQUEST, gitchanAccessToken, gitchanCafeId);

        Long gitchanCafeCouponId = 쿠폰_생성_요청하고_아이디_반환(gitchanAccessToken, new CouponCreateRequest(gitchanCafeId), customer.getId());

        ExtractableResponse<Response> response = 쿠폰_삭제_요청(customerAccessToken, gitchanCafeCouponId);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(NO_CONTENT.value()),
                () -> assertThat(couponRepository.findById(gitchanCafeCouponId)).isEmpty()
        );
    }
}
