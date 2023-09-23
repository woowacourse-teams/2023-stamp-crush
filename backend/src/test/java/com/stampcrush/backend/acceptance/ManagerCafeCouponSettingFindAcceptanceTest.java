package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.cafe.response.CafeCouponSettingFindResponse;
import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCouponSettingFindStep.카페의_현재_쿠폰_디자인_정책_조회_요청;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCouponSettingFindStep.쿠폰이_발급될때의_쿠폰_디자인_조회_요청;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.*;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

class ManagerCafeCouponSettingFindAcceptanceTest extends AcceptanceTest {

    @Autowired
    private AuthTokensGenerator authTokensGenerator;

    @Test
    void 카페_사장은_쿠폰이_발급되었을_때_쿠폰_디자인_조회_가능하다() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        String customerAccessToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST);
        Long customerId = authTokensGenerator.extractMemberId(customerAccessToken);


        Long cafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, CAFE_CREATE_REQUEST);


        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(cafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(ownerAccessToken, couponCreateRequest, customerId);


        // when
        ExtractableResponse<Response> response = 쿠폰이_발급될때의_쿠폰_디자인_조회_요청(ownerAccessToken, cafeId, couponId);
        CafeCouponSettingFindResponse cafeCouponSettingFindResponse = response.body().as(CafeCouponSettingFindResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }

    @Test
    void 내_카페에서_발급한_쿠폰이_아니면_쿠폰이_발급되었을때의_디자인_조회_불가능하다() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        String notOwnerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST_2);

        Long cafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, CAFE_CREATE_REQUEST);

        String customerAccessToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST);
        Long customerId = authTokensGenerator.extractMemberId(customerAccessToken);

        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(cafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(ownerAccessToken, couponCreateRequest, customerId);

        // when
        ExtractableResponse<Response> response = 쿠폰이_발급될때의_쿠폰_디자인_조회_요청(notOwnerAccessToken, cafeId, couponId);

        // then
        assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
    }

    @Test
    void 카페_사장은_카페의_현재_쿠폰_디자인_정책_조회_가능하다() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);

        Long cafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, CAFE_CREATE_REQUEST);

        // when
        ExtractableResponse<Response> response = 카페의_현재_쿠폰_디자인_정책_조회_요청(ownerAccessToken, cafeId);
        CafeCouponSettingFindResponse cafeCouponSettingFindResponse = response.body().as(CafeCouponSettingFindResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());

    }

    @Test
    void 내카페가_아닌_카페의_현재_쿠폰_디자인_정책_조회_불가능하다() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        String notOwnerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST_2);

        Long cafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, CAFE_CREATE_REQUEST);

        // when
        ExtractableResponse<Response> response = 카페의_현재_쿠폰_디자인_정책_조회_요청(notOwnerAccessToken, cafeId);

        // then
        assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
    }
}
