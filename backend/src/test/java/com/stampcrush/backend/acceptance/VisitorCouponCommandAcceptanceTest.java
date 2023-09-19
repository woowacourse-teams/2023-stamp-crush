package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.visitor.coupon.response.CustomerCouponsFindResponse;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환_2;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorCouponDeleteStep.쿠폰_삭제_요청;
import static com.stampcrush.backend.acceptance.step.VisitorCouponFindStep.고객의_쿠폰_카페별로_1개씩_조회_요청;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class VisitorCouponCommandAcceptanceTest extends AcceptanceTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthTokensGenerator authTokensGenerator;

    @Test
    void 쿠폰을_삭제한다() {
        // given
        String leoToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterCustomerCreateRequest("leo", "email", OAuthProvider.KAKAO, 123L));
        Long leoId = authTokensGenerator.extractMemberId(leoToken);
        Customer leo = customerRepository.findById(leoId).get();

        String ownerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterOwnerCreateRequest("ceo", OAuthProvider.KAKAO, 123L));
        Long ownerId = authTokensGenerator.extractMemberId(ownerToken);
        Owner owner = ownerRepository.findById(ownerId).get();

        Long cafeId = 카페_생성_요청하고_아이디_반환_2(ownerToken, new CafeCreateRequest("cafe", "road", "detail", "business"));

        Long couponId = 쿠폰_생성_요청하고_아이디_반환(owner, new CouponCreateRequest(cafeId), leoId);
        Response response = 고객의_쿠폰_카페별로_1개씩_조회_요청(leo).response();
        CustomerCouponsFindResponse couponResultNotYetDeleted = response.as(CustomerCouponsFindResponse.class);

        // when
        Response deleteResponse = 쿠폰_삭제_요청(leoToken, couponId).response();
        Response afterDeleteResponse = 고객의_쿠폰_카페별로_1개씩_조회_요청(leo).response();

        // then
        assertAll(
                () -> assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(afterDeleteResponse.as(CustomerCouponsFindResponse.class).getCoupons()).isEmpty()
        );
    }
}
