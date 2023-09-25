package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.manager.reward.response.RewardFindResponse;
import com.stampcrush.backend.api.manager.reward.response.RewardsFindResponse;
import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.stampcrush.backend.acceptance.ManagerRewardCommandAcceptanceTest.*;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.ManagerRewardStep.리워드_목록_조회;
import static com.stampcrush.backend.acceptance.step.ManagerStampCreateStep.쿠폰에_스탬프를_적립_요청;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.auth.OAuthProvider.KAKAO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

class ManagerRewardFindAcceptanceTest extends AcceptanceTest {

    @Test
    void 카페사장이_가입_회원의_리워드를_조회한다() {
        // given
        OAuthRegisterCustomerCreateRequest registerCustomerCreateRequest = O_AUTH_REGISTER_CUSTOMER_CREATE_REQUEST_JENA;
        String accessTokenCustomer = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(registerCustomerCreateRequest);
        Long customerId = authTokensGenerator.extractMemberId(accessTokenCustomer);

        OAuthRegisterOwnerCreateRequest registerOwnerCreateRequest = O_AUTH_OWNER_CREATE_REQUEST;
        String accessTokenOwner = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(registerOwnerCreateRequest);

        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("cafe", "잠실", "루터회관", "111111111");
        Long cafeId = 카페_생성_요청하고_아이디_반환(accessTokenOwner, cafeCreateRequest);
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(cafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(accessTokenOwner, couponCreateRequest, customerId);
        StampCreateRequest stampCreateRequest = new StampCreateRequest(10);
        쿠폰에_스탬프를_적립_요청(accessTokenOwner, customerId, couponId, stampCreateRequest);

        // when
        ExtractableResponse<Response> rewardsResponse = 리워드_목록_조회(accessTokenOwner, cafeId, customerId);
        List<RewardFindResponse> rewards = rewardsResponse.body().as(RewardsFindResponse.class).getRewards();

        // then
        assertThat(rewards.size()).isEqualTo(1);
    }

    @Test
    void 자신의_카페가_아니면_리워드_조회_불가능하다() {
        // given
        OAuthRegisterCustomerCreateRequest registerCustomerCreateRequest = O_AUTH_REGISTER_CUSTOMER_CREATE_REQUEST_JENA;
        String accessTokenCustomer = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(registerCustomerCreateRequest);
        Long customerId = authTokensGenerator.extractMemberId(accessTokenCustomer);

        OAuthRegisterOwnerCreateRequest registerOwnerCreateRequest = O_AUTH_OWNER_CREATE_REQUEST;
        String accessTokenOwner = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(registerOwnerCreateRequest);

        OAuthRegisterOwnerCreateRequest registerNotOwnerCreateRequest = O_AUTH_OWNER_CREATE_REQUEST_2;
        String accessTokenNotOwner = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(registerNotOwnerCreateRequest);

        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("cafe", "잠실", "루터회관", "111111111");
        Long cafeId = 카페_생성_요청하고_아이디_반환(accessTokenOwner, cafeCreateRequest);
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(cafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(accessTokenOwner, couponCreateRequest, customerId);
        StampCreateRequest stampCreateRequest = new StampCreateRequest(10);
        쿠폰에_스탬프를_적립_요청(accessTokenOwner, customerId, couponId, stampCreateRequest);

        // when
        ExtractableResponse<Response> response = 리워드_목록_조회(accessTokenNotOwner, cafeId, customerId);

        // then
        assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
    }

    @Test
    void 자신의_카페_고객이_아니면_리워드_조회가_불가능하다() {
        // given
        OAuthRegisterOwnerCreateRequest registerOwnerCreateRequest = O_AUTH_OWNER_CREATE_REQUEST;
        String accessTokenOwner = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(registerOwnerCreateRequest);

        OAuthRegisterCustomerCreateRequest registerNotCustomerCreateRequest =
                new OAuthRegisterCustomerCreateRequest("notCustomer", "aa@naver.com", KAKAO, 392816L);
        String accessTokenNotCustomer = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(registerNotCustomerCreateRequest);
        Long notMyCustomerId = authTokensGenerator.extractMemberId(accessTokenNotCustomer);

        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("cafe", "잠실", "루터회관", "111111111");
        Long cafeId = 카페_생성_요청하고_아이디_반환(accessTokenOwner, cafeCreateRequest);

        // when
        ExtractableResponse<Response> response = 리워드_목록_조회(accessTokenOwner, cafeId, notMyCustomerId);

        // then
        assertThat(response.statusCode()).isEqualTo(NOT_FOUND.value());
    }
}
