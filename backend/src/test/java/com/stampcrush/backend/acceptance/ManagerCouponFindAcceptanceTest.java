package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.acceptance.step.VisitorJoinStep;
import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.manager.coupon.response.CafeCustomerFindResponse;
import com.stampcrush.backend.api.manager.coupon.response.CafeCustomersFindResponse;
import com.stampcrush.backend.api.manager.coupon.response.CustomerAccumulatingCouponFindResponse;
import com.stampcrush.backend.api.manager.customer.request.TemporaryCustomerCreateRequest;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import com.stampcrush.backend.entity.user.Customer;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponFindStep.고객의_쿠폰_조회_요청;
import static com.stampcrush.backend.acceptance.step.ManagerCouponFindStep.고객의_쿠폰_조회하고_결과_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCustomerFindStep.고객_목록_조회_요청;
import static com.stampcrush.backend.acceptance.step.ManagerCustomerFindStep.고객_타입_별_목록_조회_요청;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.OWNER_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.OWNER_CREATE_REQUEST_2;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.ManagerStampCreateStep.쿠폰에_스탬프를_적립_요청;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

class ManagerCouponFindAcceptanceTest extends AcceptanceTest {

    @Test
    void 고객_목록을_조회한다() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);

        Long savedCafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, CAFE_CREATE_REQUEST);

        OAuthRegisterCustomerCreateRequest request1 =
                new OAuthRegisterCustomerCreateRequest("youngho", "e", OAuthProvider.KAKAO, 293827L);
        OAuthRegisterCustomerCreateRequest request2 =
                new OAuthRegisterCustomerCreateRequest("gitchan", "m", OAuthProvider.KAKAO, 483726L);

        String younghoToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(request1);
        Long younghoId = authTokensGenerator.extractMemberId(younghoToken);
        Customer youngho = customerRepository.findById(younghoId).get();

        String gitchanToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(request2);
        Long gitchanId = authTokensGenerator.extractMemberId(gitchanToken);
        Customer gitchan = customerRepository.findById(gitchanId).get();

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);

        Long couponId1 = 쿠폰_생성_요청하고_아이디_반환(ownerAccessToken, request, younghoId);
        Long couponId2 = 쿠폰_생성_요청하고_아이디_반환(ownerAccessToken, request, gitchanId);

        StampCreateRequest stampCreateRequest1 = new StampCreateRequest(7);
        StampCreateRequest stampCreateRequest2 = new StampCreateRequest(5);

        쿠폰에_스탬프를_적립_요청(ownerAccessToken, younghoId, couponId1, stampCreateRequest1);
        쿠폰에_스탬프를_적립_요청(ownerAccessToken, younghoId, couponId1, stampCreateRequest2);
        쿠폰에_스탬프를_적립_요청(ownerAccessToken, gitchanId, couponId2, stampCreateRequest1);

        String firstVisitDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));

        // when
        CafeCustomerFindResponse expected1 = new CafeCustomerFindResponse(1L, youngho.getNickname(), 2, 1, 2, 10, firstVisitDate, true, null);
        CafeCustomerFindResponse expected2 = new CafeCustomerFindResponse(2L, gitchan.getNickname(), 7, 0, 1, 10, firstVisitDate, true, null);

        ExtractableResponse<Response> response = 고객_목록_조회_요청(ownerAccessToken, savedCafeId);

        CafeCustomersFindResponse actual = response.body().as(CafeCustomersFindResponse.class);

        // then
        assertThat(actual.getCustomers()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("recentVisitDate").containsExactlyInAnyOrder(expected1, expected2);
    }

    @Test
    void 가입고객_목록을_조회한다() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);

        Long savedCafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, CAFE_CREATE_REQUEST);

        OAuthRegisterCustomerCreateRequest regCustomerRequest1 =
                new OAuthRegisterCustomerCreateRequest("youngho", "e", OAuthProvider.KAKAO, 293827L);
        OAuthRegisterCustomerCreateRequest regCustomerRequest2 =
                new OAuthRegisterCustomerCreateRequest("gitchan", "m", OAuthProvider.KAKAO, 483726L);
        TemporaryCustomerCreateRequest temporaryCustomerCreateRequest = new TemporaryCustomerCreateRequest("01012345678");

        Long tempCustomerId = VisitorJoinStep.임시_고객_회원_가입_요청하고_아이디_반환(ownerAccessToken, temporaryCustomerCreateRequest);
        Customer tempCustomer = customerRepository.findById(tempCustomerId).get();

        String younghoToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(regCustomerRequest1);
        Long younghoId = authTokensGenerator.extractMemberId(younghoToken);
        Customer youngho = customerRepository.findById(younghoId).get();

        String gitchanToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(regCustomerRequest2);
        Long gitchanId = authTokensGenerator.extractMemberId(gitchanToken);
        Customer gitchan = customerRepository.findById(gitchanId).get();

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);

        Long couponId1 = 쿠폰_생성_요청하고_아이디_반환(ownerAccessToken, request, younghoId);
        Long couponId2 = 쿠폰_생성_요청하고_아이디_반환(ownerAccessToken, request, gitchanId);
        Long couponId3 = 쿠폰_생성_요청하고_아이디_반환(ownerAccessToken, request, tempCustomerId);

        StampCreateRequest stampCreateRequest1 = new StampCreateRequest(7);
        StampCreateRequest stampCreateRequest2 = new StampCreateRequest(5);
        StampCreateRequest stampCreateRequest3 = new StampCreateRequest(2);

        쿠폰에_스탬프를_적립_요청(ownerAccessToken, younghoId, couponId1, stampCreateRequest1);
        쿠폰에_스탬프를_적립_요청(ownerAccessToken, younghoId, couponId1, stampCreateRequest2);
        쿠폰에_스탬프를_적립_요청(ownerAccessToken, gitchanId, couponId2, stampCreateRequest1);
        쿠폰에_스탬프를_적립_요청(ownerAccessToken, tempCustomerId, couponId3, stampCreateRequest3);

        String firstVisitDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));

        // when
        CafeCustomerFindResponse expected1 = new CafeCustomerFindResponse(1L, youngho.getNickname(), 2, 1, 2, 10, firstVisitDate, true, null);
        CafeCustomerFindResponse expected2 = new CafeCustomerFindResponse(2L, gitchan.getNickname(), 7, 0, 1, 10, firstVisitDate, true, null);

        ExtractableResponse<Response> response = 고객_타입_별_목록_조회_요청(ownerAccessToken, savedCafeId, "register");

        CafeCustomersFindResponse actual = response.body().as(CafeCustomersFindResponse.class);
        // then
        assertThat(actual.getCustomers()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("recentVisitDate", "firstVisitDate", "id").containsExactlyInAnyOrder(expected1, expected2);
    }

    @Test
    void 임시고객_목록을_조회한다() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);

        Long savedCafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, CAFE_CREATE_REQUEST);

        OAuthRegisterCustomerCreateRequest regCustomerRequest1 =
                new OAuthRegisterCustomerCreateRequest("youngho", "e", OAuthProvider.KAKAO, 293827L);
        OAuthRegisterCustomerCreateRequest regCustomerRequest2 =
                new OAuthRegisterCustomerCreateRequest("gitchan", "m", OAuthProvider.KAKAO, 483726L);
        TemporaryCustomerCreateRequest temporaryCustomerCreateRequest = new TemporaryCustomerCreateRequest("01012345678");

        Long tempCustomerId = VisitorJoinStep.임시_고객_회원_가입_요청하고_아이디_반환(ownerAccessToken, temporaryCustomerCreateRequest);
        Customer tempCustomer = customerRepository.findById(tempCustomerId).get();

        String younghoToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(regCustomerRequest1);
        Long younghoId = authTokensGenerator.extractMemberId(younghoToken);
        Customer youngho = customerRepository.findById(younghoId).get();

        String gitchanToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(regCustomerRequest2);
        Long gitchanId = authTokensGenerator.extractMemberId(gitchanToken);
        Customer gitchan = customerRepository.findById(gitchanId).get();

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);

        Long couponId1 = 쿠폰_생성_요청하고_아이디_반환(ownerAccessToken, request, younghoId);
        Long couponId2 = 쿠폰_생성_요청하고_아이디_반환(ownerAccessToken, request, gitchanId);
        Long couponId3 = 쿠폰_생성_요청하고_아이디_반환(ownerAccessToken, request, tempCustomerId);

        StampCreateRequest stampCreateRequest1 = new StampCreateRequest(7);
        StampCreateRequest stampCreateRequest2 = new StampCreateRequest(5);
        StampCreateRequest stampCreateRequest3 = new StampCreateRequest(2);

        쿠폰에_스탬프를_적립_요청(ownerAccessToken, younghoId, couponId1, stampCreateRequest1);
        쿠폰에_스탬프를_적립_요청(ownerAccessToken, younghoId, couponId1, stampCreateRequest2);
        쿠폰에_스탬프를_적립_요청(ownerAccessToken, gitchanId, couponId2, stampCreateRequest1);
        쿠폰에_스탬프를_적립_요청(ownerAccessToken, tempCustomerId, couponId3, stampCreateRequest3);

        String firstVisitDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));

        // when
        CafeCustomerFindResponse expected = new CafeCustomerFindResponse(1L, tempCustomer.getNickname(), 2, 0, 1, 10, firstVisitDate, false, null);

        ExtractableResponse<Response> response = 고객_타입_별_목록_조회_요청(ownerAccessToken, savedCafeId, "temporary");

        CafeCustomersFindResponse actual = response.body().as(CafeCustomersFindResponse.class);
        // then
        assertThat(actual.getCustomers()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("recentVisitDate", "firstVisitDate", "id").containsExactlyInAnyOrder(expected);
    }

    @Test
    void 고객_타입_별_조회_시_존재하지_않는_타입으로_조회_시_예외발생() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, CAFE_CREATE_REQUEST);

        // when
        ExtractableResponse<Response> response = 고객_타입_별_목록_조회_요청(ownerAccessToken, savedCafeId, "invalid");

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void 내_카페가_아닌_고객의_고객목록_조회_불가능() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        String notOwnerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST_2);

        Long savedCafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, CAFE_CREATE_REQUEST);
        OAuthRegisterCustomerCreateRequest regCustomerRequest =
                new OAuthRegisterCustomerCreateRequest("gitchan", "m", OAuthProvider.KAKAO, 483726L);
        String regCustomerAccessToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(regCustomerRequest);
        Long regCustomerId = authTokensGenerator.extractMemberId(regCustomerAccessToken);
        Customer gitchan = customerRepository.findById(regCustomerId).get();

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);

        Long couponId2 = 쿠폰_생성_요청하고_아이디_반환(ownerAccessToken, request, gitchan.getId());

        // when
        ExtractableResponse<Response> response = 고객_목록_조회_요청(notOwnerAccessToken, savedCafeId);
        // then
        assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
    }

    @Test
    void 고객의_적립중인_쿠폰을_조회한다() {
        // given
        String ownerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterOwnerCreateRequest("jena", OAuthProvider.KAKAO, 123L));
        Long managerId = authTokensGenerator.extractMemberId(ownerToken);

        Long savedCafeId = 카페_생성_요청하고_아이디_반환(ownerToken, CAFE_CREATE_REQUEST);

        String leoVisitorToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterCustomerCreateRequest("leo", "email", OAuthProvider.KAKAO, 123L));
        Long leoVisitorId = authTokensGenerator.extractMemberId(leoVisitorToken);
        Customer leoVisitor = customerRepository.findById(leoVisitorId).get();

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);

        Long couponId = 쿠폰_생성_요청하고_아이디_반환(ownerToken, request, leoVisitorId);

        StampCreateRequest stampCreateRequest1 = new StampCreateRequest(7);
        StampCreateRequest stampCreateRequest2 = new StampCreateRequest(5);

        쿠폰에_스탬프를_적립_요청(ownerToken, leoVisitorId, couponId, stampCreateRequest1);
        쿠폰에_스탬프를_적립_요청(ownerToken, leoVisitorId, couponId, stampCreateRequest2);

        // when
        String expireDate = LocalDate.now().plusMonths(6).format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        CustomerAccumulatingCouponFindResponse expected = new CustomerAccumulatingCouponFindResponse(couponId + 1, leoVisitor.getId(), leoVisitor.getNickname(), 2, expireDate, Boolean.FALSE, 10);
        List<CustomerAccumulatingCouponFindResponse> response = 고객의_쿠폰_조회하고_결과_반환(ownerToken, savedCafeId, leoVisitorId);

        // then
        assertThat(response).containsExactlyInAnyOrder(expected);
    }

    @Test
    void 내_카페가_아닌_카페의_고객의_쿠폰은_조회_불가능() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        String notOwnerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST_2);

        Long savedCafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, CAFE_CREATE_REQUEST);

        String leoVisitorToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(new OAuthRegisterCustomerCreateRequest("leo", "email", OAuthProvider.KAKAO, 123L));
        Long leoVisitorId = authTokensGenerator.extractMemberId(leoVisitorToken);
        Customer youngho = customerRepository.findById(leoVisitorId).get();

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(ownerAccessToken, request, youngho.getId());

        StampCreateRequest stampCreateRequest1 = new StampCreateRequest(7);
        쿠폰에_스탬프를_적립_요청(ownerAccessToken, youngho.getId(), couponId, stampCreateRequest1);

        // when
        ExtractableResponse<Response> response = 고객의_쿠폰_조회_요청(savedCafeId, notOwnerAccessToken, youngho.getId());

        // then
        assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
    }
}
