package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.entity.cafe.Cafe;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCouponSettingUpdateStep.CAFE_COUPON_SETTING_UPDATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCouponSettingUpdateStep.카페_쿠폰_정책_수정_요청;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

class ManagerCafeCouponSettingCommandUpdateAcceptanceTest extends AcceptanceTest {

    @Test
    void 카페_사장은_쿠폰_세팅에_대한_내용을_수정할_수_있다() {
        // given, when
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        Long cafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, CAFE_CREATE_REQUEST);

        ExtractableResponse<Response> response = 카페_쿠폰_정책_수정_요청(CAFE_COUPON_SETTING_UPDATE_REQUEST, ownerAccessToken, cafeId);
        Cafe cafe = cafeRepository.findById(cafeId).get();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(NO_CONTENT.value()),
                () -> assertThat(cafeCouponDesignRepository.findByCafe(cafe).get().getFrontImageUrl()).isEqualTo(CAFE_COUPON_SETTING_UPDATE_REQUEST.getFrontImageUrl()),
                () -> assertThat(cafeCouponDesignRepository.findByCafe(cafe).get().getBackImageUrl()).isEqualTo(CAFE_COUPON_SETTING_UPDATE_REQUEST.getBackImageUrl()),
                () -> assertThat(cafeCouponDesignRepository.findByCafe(cafe).get().getStampImageUrl()).isEqualTo(CAFE_COUPON_SETTING_UPDATE_REQUEST.getStampImageUrl()),
                () -> assertThat(cafePolicyRepository.findByCafe(cafe).get().getExpirePeriod()).isEqualTo(CAFE_COUPON_SETTING_UPDATE_REQUEST.getExpirePeriod()),
                () -> assertThat(cafePolicyRepository.findByCafe(cafe).get().getReward()).isEqualTo(CAFE_COUPON_SETTING_UPDATE_REQUEST.getReward())
        );
    }

    @Test
    void 카페_사장은_자신의_카페가_아니면_쿠폰_세팅에_대한_내용을_수정할_수_없다() {
        // given, when
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        String notOwnerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST_2);

        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("woowacafe", "road", "detail", "1234");
        Long cafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, cafeCreateRequest);

        ExtractableResponse<Response> response = 카페_쿠폰_정책_수정_요청(CAFE_COUPON_SETTING_UPDATE_REQUEST, notOwnerAccessToken, cafeId);

        // then
        assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
    }
}
