package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.visitor.visithistory.response.CustomerStampHistoriesFindResponse;
import com.stampcrush.backend.api.visitor.visithistory.response.CustomerStampHistoryFindResponse;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.ManagerStampCreateStep.쿠폰에_스탬프를_적립_요청;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorVisitHistoriesFindStep.고객의_카페_방문_이력_조회;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class VisitorVisitHistoryFindAcceptanceTest extends AcceptanceTest {

    @Test
    void 고객의_카페_방문_이력을_조회한다() {
        // given
        OAuthRegisterCustomerCreateRequest oauthRegister = new OAuthRegisterCustomerCreateRequest("leo", "email", OAuthProvider.KAKAO, 123L);
        String customerAccessToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(oauthRegister);
        Long customerId = authTokensGenerator.extractMemberId(customerAccessToken);

        OAuthRegisterOwnerCreateRequest leoManager = new OAuthRegisterOwnerCreateRequest("leoManager", OAuthProvider.KAKAO, 123L);
        String leoManagerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(leoManager);
        Long leoCafe = 카페_생성_요청하고_아이디_반환(leoManagerToken, new CafeCreateRequest(
                "leoCafe",
                "송파구",
                "상세주소",
                "zzz"
        ));

        OAuthRegisterOwnerCreateRequest hardyManager = new OAuthRegisterOwnerCreateRequest("hardy", OAuthProvider.KAKAO, 123L);
        String hardyManagerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(hardyManager);
        Long hardyCafe = 카페_생성_요청하고_아이디_반환(hardyManagerToken, new CafeCreateRequest(
                "hardyCafe",
                "설입",
                "어딘가",
                "qqq"
        ));

        Long leoCafeCouponId = 쿠폰_생성_요청하고_아이디_반환(leoManagerToken, new CouponCreateRequest(leoCafe), customerId);
        Long hardyCafeCouponId = 쿠폰_생성_요청하고_아이디_반환(hardyManagerToken, new CouponCreateRequest(hardyCafe), customerId);
        쿠폰에_스탬프를_적립_요청(leoManagerToken, customerId, leoCafeCouponId, new StampCreateRequest(2));
        쿠폰에_스탬프를_적립_요청(hardyManagerToken, customerId, hardyCafeCouponId, new StampCreateRequest(2));
        쿠폰에_스탬프를_적립_요청(leoManagerToken, customerId, leoCafeCouponId, new StampCreateRequest(1));
        쿠폰에_스탬프를_적립_요청(hardyManagerToken, customerId, hardyCafeCouponId, new StampCreateRequest(2));
        쿠폰에_스탬프를_적립_요청(hardyManagerToken, customerId, hardyCafeCouponId, new StampCreateRequest(3));

        // when
        ExtractableResponse<Response> response = 고객의_카페_방문_이력_조회(customerAccessToken);
        CustomerStampHistoriesFindResponse result = response.body().as(CustomerStampHistoriesFindResponse.class);

        CustomerStampHistoryFindResponse historyResponse1 = new CustomerStampHistoryFindResponse(1L, "hardyCafe", 2, "aaa");
        CustomerStampHistoryFindResponse historyResponse2 = new CustomerStampHistoryFindResponse(2L, "hardyCafe", 2, "aaa");
        CustomerStampHistoryFindResponse historyResponse3 = new CustomerStampHistoryFindResponse(3L, "hardyCafe", 3, "aaa");
        CustomerStampHistoryFindResponse historyResponse4 = new CustomerStampHistoryFindResponse(4L, "leoCafe", 2, "aaa");
        CustomerStampHistoryFindResponse historyResponse5 = new CustomerStampHistoryFindResponse(5L, "leoCafe", 1, "aaa");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(result.getStampHistories()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("createdAt", "id")
                        .contains(historyResponse1, historyResponse2, historyResponse3, historyResponse4, historyResponse5)
        );
    }
}
