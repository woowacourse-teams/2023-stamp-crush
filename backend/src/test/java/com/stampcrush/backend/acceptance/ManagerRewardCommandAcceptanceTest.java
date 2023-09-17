package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.manager.reward.request.RewardUsedUpdateRequest;
import com.stampcrush.backend.api.manager.reward.response.RewardFindResponse;
import com.stampcrush.backend.api.manager.reward.response.RewardsFindResponse;
import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환_2;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청하고_아이디_반환_2;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.*;
import static com.stampcrush.backend.acceptance.step.ManagerRewardStep.리워드_목록_조회;
import static com.stampcrush.backend.acceptance.step.ManagerRewardStep.리워드_사용;
import static com.stampcrush.backend.acceptance.step.ManagerStampCreateStep.쿠폰에_스탬프를_적립_요청_2;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

class ManagerRewardCommandAcceptanceTest extends AcceptanceTest {

    @Test
    void 카페사장이_가입_회원의_리워드를_사용한다() {
        // given
        OAuthRegisterCustomerCreateRequest registerCustomerCreateRequest = O_AUTH_REGISTER_CUSTOMER_CREATE_REQUEST_JENA;
        String accessTokenCustomer = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(registerCustomerCreateRequest);
        Long customerId = authTokensGenerator.extractMemberId(accessTokenCustomer);

        OAuthRegisterOwnerCreateRequest registerOwnerCreateRequest = O_AUTH_OWNER_CREATE_REQUEST;
        String accessTokenOwner = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(registerOwnerCreateRequest);

        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("cafe", "잠실", "루터회관", "111111111");
        Long cafeId = 카페_생성_요청하고_아이디_반환_2(accessTokenOwner, cafeCreateRequest);

        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(cafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환_2(accessTokenOwner, couponCreateRequest, customerId);
        StampCreateRequest stampCreateRequest = new StampCreateRequest(10);
        쿠폰에_스탬프를_적립_요청_2(accessTokenOwner, customerId, couponId, stampCreateRequest);
        ExtractableResponse<Response> response = 리워드_목록_조회(accessTokenOwner, cafeId, customerId);
        List<RewardFindResponse> rewards = response.body().as(RewardsFindResponse.class).getRewards();
        Long rewardId = rewards.get(0).getId();

        //when
        RewardUsedUpdateRequest request = new RewardUsedUpdateRequest(cafeId, true);
        리워드_사용(accessTokenOwner, request, customerId, rewardId);
        ExtractableResponse<Response> actual = 리워드_목록_조회(accessTokenOwner, cafeId, customerId);
        List<RewardFindResponse> restRewards = actual.body().as(RewardsFindResponse.class).getRewards();

        // then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(rewards.size()).isEqualTo(1);
        softAssertions.assertThat(restRewards.size()).isEqualTo(0);
        softAssertions.assertAll();
    }

    @Test
    void 카페사장이_임시_회원의_리워드를_사용할_수_없다() {
        // given
        Long customerId = 임시_고객_회원_가입_요청하고_아이디_반환("01011111111");

        OAuthRegisterOwnerCreateRequest registerOwnerCreateRequest = O_AUTH_OWNER_CREATE_REQUEST;
        String accessTokenOwner = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(registerOwnerCreateRequest);

        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("cafe", "잠실", "루터회관", "111111111");
        Long cafeId = 카페_생성_요청하고_아이디_반환_2(accessTokenOwner, cafeCreateRequest);
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(cafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환_2(accessTokenOwner, couponCreateRequest, customerId);
        StampCreateRequest stampCreateRequest = new StampCreateRequest(10);
        쿠폰에_스탬프를_적립_요청_2(accessTokenOwner, customerId, couponId, stampCreateRequest);
        ExtractableResponse<Response> response = 리워드_목록_조회(accessTokenOwner, cafeId, customerId);
        List<RewardFindResponse> rewards = response.body().as(RewardsFindResponse.class).getRewards();
        Long rewardId = rewards.get(0).getId();

        //when
        RewardUsedUpdateRequest request = new RewardUsedUpdateRequest(cafeId, true);
        리워드_사용(accessTokenOwner, request, customerId, rewardId);
        ExtractableResponse<Response> actual = 리워드_목록_조회(accessTokenOwner, cafeId, customerId);
        List<RewardFindResponse> restRewards = actual.body().as(RewardsFindResponse.class).getRewards();

        // then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(rewards.size()).isEqualTo(1);
        softAssertions.assertThat(restRewards.size()).isEqualTo(1);
        softAssertions.assertAll();
    }

    @Test
    void 카페_사장이_자신의_카페_리워드가_아니면_사용할_수_없다() {
        // given
        OAuthRegisterCustomerCreateRequest registerCustomerCreateRequest = O_AUTH_REGISTER_CUSTOMER_CREATE_REQUEST_JENA;
        String accessTokenCustomer = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(registerCustomerCreateRequest);
        Long customerId = authTokensGenerator.extractMemberId(accessTokenCustomer);

        OAuthRegisterOwnerCreateRequest registerOwnerCreateRequest = O_AUTH_OWNER_CREATE_REQUEST;
        String accessTokenOwner = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(registerOwnerCreateRequest);

        OAuthRegisterOwnerCreateRequest registerNotOwnerCreateRequest = O_AUTH_OWNER_CREATE_REQUEST_2;
        String accessTokenNotOwner = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(registerNotOwnerCreateRequest);

        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("cafe", "잠실", "루터회관", "111111111");
        Long cafeId = 카페_생성_요청하고_아이디_반환_2(accessTokenOwner, cafeCreateRequest);
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(cafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환_2(accessTokenOwner, couponCreateRequest, customerId);
        StampCreateRequest stampCreateRequest = new StampCreateRequest(10);
        쿠폰에_스탬프를_적립_요청_2(accessTokenOwner, customerId, couponId, stampCreateRequest);
        ExtractableResponse<Response> rewardsResponse = 리워드_목록_조회(accessTokenOwner, cafeId, customerId);
        List<RewardFindResponse> rewards = rewardsResponse.body().as(RewardsFindResponse.class).getRewards();
        Long rewardId = rewards.get(0).getId();

        //when
        RewardUsedUpdateRequest request = new RewardUsedUpdateRequest(cafeId, true);
        ExtractableResponse<Response> response = 리워드_사용(accessTokenNotOwner, request, customerId, rewardId);

        // then
        assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
    }
}
