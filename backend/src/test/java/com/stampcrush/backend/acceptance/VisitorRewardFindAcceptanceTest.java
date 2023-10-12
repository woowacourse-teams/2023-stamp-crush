package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.visitor.reward.response.VisitorRewardsFindResponse;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.OWNER_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.ManagerStampCreateStep.쿠폰에_스탬프를_적립_요청;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorRewardFindStep.리워드_목록_조회;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.OK;

class VisitorRewardFindAcceptanceTest extends AcceptanceTest {

    @Test
    void 고객이_자신의_리워드를_조회한다() {
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, CAFE_CREATE_REQUEST);

        String customerToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterCustomerCreateRequest("customer", "email", OAuthProvider.KAKAO, 123L));
        Long customerId = authTokensGenerator.extractMemberId(customerToken);

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);

        Long couponId = 쿠폰_생성_요청하고_아이디_반환(ownerAccessToken, request, customerId);

        int stampCountEnoughToCreateReward = 10;
        StampCreateRequest stampCreateRequest = new StampCreateRequest(stampCountEnoughToCreateReward);

        쿠폰에_스탬프를_적립_요청(ownerAccessToken, customerId, couponId, stampCreateRequest);

        // when
        ExtractableResponse<Response> response = 리워드_목록_조회(customerToken);
        VisitorRewardsFindResponse result = response.body().as(VisitorRewardsFindResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(OK.value()),
                () -> assertThat(result.getRewards()).isNotEmpty()
        );
    }

    @Test
    void 고객이_자신의_리워드를_조회할_때_리워드가_없으면_빈_배열을_반환한다() {
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, CAFE_CREATE_REQUEST);

        String customerToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterCustomerCreateRequest("customer", "email", OAuthProvider.KAKAO, 123L));
        Long customerId = authTokensGenerator.extractMemberId(customerToken);


        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);

        Long couponId = 쿠폰_생성_요청하고_아이디_반환(ownerAccessToken, request, customerId);

        int stampCountNotEnoughToCreateReward = 1;
        StampCreateRequest stampCreateRequest = new StampCreateRequest(stampCountNotEnoughToCreateReward);

        쿠폰에_스탬프를_적립_요청(ownerAccessToken, customerId, couponId, stampCreateRequest);

        // when
        ExtractableResponse<Response> response = 리워드_목록_조회(customerToken);
        VisitorRewardsFindResponse result = response.body().as(VisitorRewardsFindResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(OK.value()),
                () -> assertThat(result.getRewards()).isEmpty()
        );
    }
}
