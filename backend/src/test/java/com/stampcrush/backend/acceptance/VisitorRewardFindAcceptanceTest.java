package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.visitor.reward.response.VisitorRewardsFindResponse;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerStampCreateStep.쿠폰에_스탬프를_적립_요청;
import static com.stampcrush.backend.acceptance.step.VisitorRewardFindStep.리워드_목록_조회;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class VisitorRewardFindAcceptanceTest extends AcceptanceTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 고객이_자신의_리워드를_조회한다() {
        Owner owner = 사장_생성();
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        Customer savedCustomer = customerRepository.save(
                Customer.registeredCustomerBuilder()
                        .nickname("name")
                        .phoneNumber("phone")
                        .loginId("id")
                        .encryptedPassword("pw")
                        .build()
        );

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);

        Long couponId = 쿠폰_생성_요청하고_아이디_반환(owner, request, savedCustomer.getId());

        int stampCountEnoughToCreateReward = 15;
        StampCreateRequest stampCreateRequest = new StampCreateRequest(stampCountEnoughToCreateReward);

        쿠폰에_스탬프를_적립_요청(owner, savedCustomer, couponId, stampCreateRequest);

        // when
        ExtractableResponse<Response> response = 리워드_목록_조회(savedCustomer.getId());
        VisitorRewardsFindResponse result = response.body().as(VisitorRewardsFindResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(result.getRewards()).isNotEmpty()
        );
    }

    @Test
    void 고객이_자신의_리워드를_조회할_때_리워드가_없으면_빈_배열을_반환한다() {
        Owner owner = 사장_생성();
        Long savedCafeId = 카페_생성_요청하고_아이디_반환(owner, CAFE_CREATE_REQUEST);
        Customer savedCustomer = customerRepository.save(
                Customer.registeredCustomerBuilder()
                        .nickname("name")
                        .phoneNumber("phone")
                        .loginId("id")
                        .encryptedPassword("pw")
                        .build()
        );

        CouponCreateRequest request = new CouponCreateRequest(savedCafeId);

        Long couponId = 쿠폰_생성_요청하고_아이디_반환(owner, request, savedCustomer.getId());

        int stampCountNotEnoughToCreateReward = 1;
        StampCreateRequest stampCreateRequest = new StampCreateRequest(stampCountNotEnoughToCreateReward);

        쿠폰에_스탬프를_적립_요청(owner, savedCustomer, couponId, stampCreateRequest);

        // when
        ExtractableResponse<Response> response = 리워드_목록_조회(savedCustomer.getId());
        VisitorRewardsFindResponse result = response.body().as(VisitorRewardsFindResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(result.getRewards()).isEmpty()
        );
    }

    private Owner 사장_생성() {
        return ownerRepository.save(new Owner("owner", "id", "pw", "phone"));
    }
}
