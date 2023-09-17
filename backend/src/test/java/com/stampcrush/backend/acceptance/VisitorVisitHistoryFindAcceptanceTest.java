package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.acceptance.step.VisitorVisitHistoriesFindStep;
import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.visitor.visithistory.response.CustomerStampHistoriesFindResponse;
import com.stampcrush.backend.api.visitor.visithistory.response.CustomerStampHistoryFindResponse;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import com.stampcrush.backend.auth.application.util.AuthTokensGenerator;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.ManagerStampCreateStep.쿠폰에_스탬프를_적립_요청;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class VisitorVisitHistoryFindAcceptanceTest extends AcceptanceTest {

    @Autowired
    private AuthTokensGenerator authTokensGenerator;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 고객의_카페_방문_이력을_조회한다() {
        // given
        OAuthRegisterCustomerCreateRequest oauthRegister = new OAuthRegisterCustomerCreateRequest("leo", "email", OAuthProvider.KAKAO, 123L);
        String customerAccessToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(oauthRegister);
        Long customerId = authTokensGenerator.extractMemberId(customerAccessToken);
        Customer customer = customerRepository.findById(customerId).get();

        OAuthRegisterOwnerCreateRequest leoManager = new OAuthRegisterOwnerCreateRequest("leoManager", OAuthProvider.KAKAO, 123L);
        String leoManagerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(leoManager);
        Long leoManagerId = authTokensGenerator.extractMemberId(leoManagerToken);
        Owner leoOwner = ownerRepository.findById(leoManagerId).get();
        Long leoCafe = 카페_생성_요청하고_아이디_반환(leoOwner, new CafeCreateRequest(
                "leoCafe",
                "송파구",
                "상세주소",
                "zzz"
        ));

        OAuthRegisterOwnerCreateRequest hardyManager = new OAuthRegisterOwnerCreateRequest("hardy", OAuthProvider.KAKAO, 123L);
        String hardyManagerToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(hardyManager);
        Long hardyManagerId = authTokensGenerator.extractMemberId(hardyManagerToken);
        Owner hardyOwner = ownerRepository.findById(hardyManagerId).get();
        Long hardyCafe = 카페_생성_요청하고_아이디_반환(hardyOwner, new CafeCreateRequest(
                "hardyCafe",
                "설입",
                "어딘가",
                "qqq"
        ));

        Long leoCafeCoupon = 쿠폰_생성_요청하고_아이디_반환(leoOwner, new CouponCreateRequest(leoCafe), customerId);
        Long hardyCafeCoupon = 쿠폰_생성_요청하고_아이디_반환(hardyOwner, new CouponCreateRequest(hardyCafe), customerId);
        쿠폰에_스탬프를_적립_요청(leoOwner, customer, leoCafeCoupon, new StampCreateRequest(2));
        쿠폰에_스탬프를_적립_요청(hardyOwner, customer, hardyCafeCoupon, new StampCreateRequest(2));
        쿠폰에_스탬프를_적립_요청(leoOwner, customer, leoCafeCoupon, new StampCreateRequest(1));
        쿠폰에_스탬프를_적립_요청(hardyOwner, customer, hardyCafeCoupon, new StampCreateRequest(2));
        쿠폰에_스탬프를_적립_요청(hardyOwner, customer, hardyCafeCoupon, new StampCreateRequest(3));

        // when
        System.out.println("실제 조회 요청");
        ExtractableResponse<Response> response = VisitorVisitHistoriesFindStep.고객의_카페_방문_이력_조회(customer);
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
