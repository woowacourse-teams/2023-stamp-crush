package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.manager.reward.request.RewardUsedUpdateRequest;
import com.stampcrush.backend.api.manager.reward.response.RewardFindResponse;
import com.stampcrush.backend.api.manager.reward.response.RewardsFindResponse;
import com.stampcrush.backend.auth.OAuthProvider;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCouponCreateStep.쿠폰_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerRewardStep.리워드_목록_조회;
import static com.stampcrush.backend.acceptance.step.ManagerRewardStep.리워드_사용;
import static com.stampcrush.backend.acceptance.step.ManagerStampCreateStep.쿠폰에_스탬프를_적립_요청;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class ManagerRewardCommandAcceptanceTest extends AcceptanceTest {

    // TODO 회원가입, 로그인 구현 후 제거
    @Autowired
    private OwnerRepository ownerRepository;

    // TODO 회원가입, 로그인 구현 후 제거
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 카페사장이_가입_회원의_리워드를_사용한다() {
        // given
        Customer customer = 가입_회원_생성_후_가입_고객_반환();
        Owner owner = 카페_사장_생성_후_사장_반환();
        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("cafe", "잠실", "루터회관", "111111111");
        Long cafeId = 카페_생성_요청하고_아이디_반환(owner, cafeCreateRequest);
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(cafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(owner, couponCreateRequest, customer.getId());
        StampCreateRequest stampCreateRequest = new StampCreateRequest(10);
        쿠폰에_스탬프를_적립_요청(owner, customer, couponId, stampCreateRequest);
        ExtractableResponse<Response> response = 리워드_목록_조회(owner, cafeId, customer.getId());
        List<RewardFindResponse> rewards = response.body().as(RewardsFindResponse.class).getRewards();
        Long rewardId = rewards.get(0).getId();

        //when
        RewardUsedUpdateRequest request = new RewardUsedUpdateRequest(cafeId, true);
        리워드_사용(owner, request, customer.getId(), rewardId);
        ExtractableResponse<Response> actual = 리워드_목록_조회(owner, cafeId, customer.getId());
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
        Customer customer = 임시_회원_생성_후_가입_고객_반환();
        Owner owner = 카페_사장_생성_후_사장_반환();
        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("cafe", "잠실", "루터회관", "111111111");
        Long cafeId = 카페_생성_요청하고_아이디_반환(owner, cafeCreateRequest);
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(cafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(owner, couponCreateRequest, customer.getId());
        StampCreateRequest stampCreateRequest = new StampCreateRequest(10);
        쿠폰에_스탬프를_적립_요청(owner, customer, couponId, stampCreateRequest);
        ExtractableResponse<Response> response = 리워드_목록_조회(owner, cafeId, customer.getId());
        List<RewardFindResponse> rewards = response.body().as(RewardsFindResponse.class).getRewards();
        Long rewardId = rewards.get(0).getId();

        //when
        RewardUsedUpdateRequest request = new RewardUsedUpdateRequest(cafeId, true);
        리워드_사용(owner, request, customer.getId(), rewardId);
        ExtractableResponse<Response> actual = 리워드_목록_조회(owner, cafeId, customer.getId());
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
        Customer customer = 가입_회원_생성_후_가입_고객_반환();
        Owner owner = 카페_사장_생성_후_사장_반환();
        Owner notOwner = ownerRepository.save(new Owner("notowner", "id", "pw", "01093726453"));

        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("cafe", "잠실", "루터회관", "111111111");
        Long cafeId = 카페_생성_요청하고_아이디_반환(owner, cafeCreateRequest);
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(cafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(owner, couponCreateRequest, customer.getId());
        StampCreateRequest stampCreateRequest = new StampCreateRequest(10);
        쿠폰에_스탬프를_적립_요청(owner, customer, couponId, stampCreateRequest);
        ExtractableResponse<Response> rewardsResponse = 리워드_목록_조회(owner, cafeId, customer.getId());
        List<RewardFindResponse> rewards = rewardsResponse.body().as(RewardsFindResponse.class).getRewards();
        Long rewardId = rewards.get(0).getId();

        //when
        RewardUsedUpdateRequest request = new RewardUsedUpdateRequest(cafeId, true);
        ExtractableResponse<Response> response = 리워드_사용(notOwner, request, customer.getId(), rewardId);

        // then
        assertThat(response.statusCode()).isEqualTo(401);
    }

    @Test
    void 카페사장이_가입_회원의_리워드를_조회한다() {
        // given
        Customer customer = 가입_회원_생성_후_가입_고객_반환();
        Owner owner = 카페_사장_생성_후_사장_반환();
        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("cafe", "잠실", "루터회관", "111111111");
        Long cafeId = 카페_생성_요청하고_아이디_반환(owner, cafeCreateRequest);
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(cafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(owner, couponCreateRequest, customer.getId());
        StampCreateRequest stampCreateRequest = new StampCreateRequest(10);
        쿠폰에_스탬프를_적립_요청(owner, customer, couponId, stampCreateRequest);

        // when
        ExtractableResponse<Response> rewardsResponse = 리워드_목록_조회(owner, cafeId, customer.getId());
        List<RewardFindResponse> rewards = rewardsResponse.body().as(RewardsFindResponse.class).getRewards();

        // then
        assertThat(rewards.size()).isEqualTo(1);
    }

    @Test
    void 자신의_카페_고객이_아니면_리워드_조회_불가능하다() {
        // given
        Customer customer = 가입_회원_생성_후_가입_고객_반환();
        Owner owner = 카페_사장_생성_후_사장_반환();
        Owner notOwner = ownerRepository.save(new Owner("notowner", "id", "pw", "01093726453"));


        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("cafe", "잠실", "루터회관", "111111111");
        Long cafeId = 카페_생성_요청하고_아이디_반환(owner, cafeCreateRequest);
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(cafeId);
        Long couponId = 쿠폰_생성_요청하고_아이디_반환(owner, couponCreateRequest, customer.getId());
        StampCreateRequest stampCreateRequest = new StampCreateRequest(10);
        쿠폰에_스탬프를_적립_요청(owner, customer, couponId, stampCreateRequest);

        // when
        ExtractableResponse<Response> response = 리워드_목록_조회(notOwner, cafeId, customer.getId());

        // then
        assertThat(response.statusCode()).isEqualTo(401);
    }

    // TODO 회원가입, 로그인 구현 후 API CAll 로 대체
    private Customer 가입_회원_생성_후_가입_고객_반환() {
        Customer customer = Customer.registeredCustomerBuilder()
                .nickname("leo")
                .email("leo@gmail.com")
                .loginId("leoId")
                .encryptedPassword("pw")
                .oAuthProvider(OAuthProvider.KAKAO)
                .oAuthId(123L)
                .build();
        return customerRepository.save(customer);
    }

    // TODO 회원가입, 로그인 구현 후 API CAll 로 대체
    private Customer 임시_회원_생성_후_가입_고객_반환() {
        Customer customer = Customer.temporaryCustomerBuilder()
                .phoneNumber("01011111111")
                .build();
        return customerRepository.save(customer);
    }

    // TODO 회원가입, 로그인 구현 후 API CAll 로 대체
    private Owner 카페_사장_생성_후_사장_반환() {
        return ownerRepository.save(new Owner("hardy", "hardyId", "1234", "01011111111"));
    }
}
