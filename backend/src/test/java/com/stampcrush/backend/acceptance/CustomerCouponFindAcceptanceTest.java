package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.user.OwnerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.stampcrush.backend.acceptance.step.CafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.CafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.CouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.CouponFindStep.고객의_쿠폰_카페별로_1개씩_조회_요청;
import static com.stampcrush.backend.acceptance.step.TemporaryCustomerCreateStep.TEMPORARY_CUSTOMER_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.TemporaryCustomerCreateStep.전화번호로_임시_고객_등록_요청하고_아이디_반환;
import static com.stampcrush.backend.fixture.OwnerFixture.GITCHAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CustomerCouponFindAcceptanceTest extends AcceptanceTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void 카페당_하나의_쿠폰을_조회할_수_있다() {
        // given
        Long customerId = 전화번호로_임시_고객_등록_요청하고_아이디_반환(TEMPORARY_CUSTOMER_CREATE_REQUEST);

        // TODO: Owner에 대한 회원가입 로직이 생기면 요청으로 대체한다.
        Owner savedOwner = ownerRepository.save(GITCHAN);
        Long cafeId = 카페_생성_요청하고_아이디_반환(savedOwner.getId(), CAFE_CREATE_REQUEST);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(new CouponCreateRequest(cafeId), customerId);

        // when
        ExtractableResponse<Response> response = 고객의_쿠폰_카페별로_1개씩_조회_요청(customerId);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("coupons")).isNotEmpty(),
                () -> assertThat(response.jsonPath().getLong("coupons[0].cafeInfo.id")).isEqualTo(cafeId),
                () -> assertThat(response.jsonPath().getLong("coupons[0].couponInfos[0].id")).isEqualTo(couponId)
        );
    }
}
