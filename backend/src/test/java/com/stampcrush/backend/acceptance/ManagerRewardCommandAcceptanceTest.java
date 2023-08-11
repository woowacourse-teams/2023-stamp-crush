package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.CouponCreateRequest;
import com.stampcrush.backend.api.manager.coupon.request.StampCreateRequest;
import com.stampcrush.backend.api.manager.reward.request.RewardUsedUpdateRequest;
import com.stampcrush.backend.api.manager.reward.response.RewardFindResponse;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import com.stampcrush.backend.repository.user.TemporaryCustomerRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class ManagerRewardCommandAcceptanceTest extends AcceptanceTest {

    // TODO 회원가입, 로그인 구현 후 제거
    @Autowired
    private OwnerRepository ownerRepository;

    // TODO 회원가입, 로그인 구현 후 제거
    @Autowired
    private RegisterCustomerRepository registerCustomerRepository;

    // TODO 회원가입, 로그인 구현 후 제거
    @Autowired
    private TemporaryCustomerRepository temporaryCustomerRepository;

    @Test
    void 카페사장이_가입_회원의_리워드를_사용한다() {
        // given
        Customer customer = 가입_회원_생성_후_가입_고객_반환();
        Owner owner = 카페_사장_생성_후_사장_반환();
        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("cafe", "잠실", "루터회관", "111111111");
        Long cafeId = 카페_생성_후_카페_아이디_반환(owner, cafeCreateRequest);
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(cafeId);
        Long couponId = 쿠폰_생성_후_쿠폰_아이디_반환(owner, couponCreateRequest, customer.getId());
        StampCreateRequest stampCreateRequest = new StampCreateRequest(10);
        스탬프_찍은_후_리워드_생성(owner, customer.getId(), couponId, stampCreateRequest);
        List<RewardFindResponse> rewards = 리워드_목록_조회(owner, cafeId, customer.getId());
        Long rewardId = rewards.get(0).getId();

        //when
        RewardUsedUpdateRequest request = new RewardUsedUpdateRequest(cafeId, true);
        리워드_사용(owner, request, customer.getId(), rewardId);
        List<RewardFindResponse> restRewards = 리워드_목록_조회(owner, cafeId, customer.getId());

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
        Long cafeId = 카페_생성_후_카페_아이디_반환(owner, cafeCreateRequest);
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(cafeId);
        Long couponId = 쿠폰_생성_후_쿠폰_아이디_반환(owner, couponCreateRequest, customer.getId());
        StampCreateRequest stampCreateRequest = new StampCreateRequest(10);
        스탬프_찍은_후_리워드_생성(owner, customer.getId(), couponId, stampCreateRequest);
        List<RewardFindResponse> rewards = 리워드_목록_조회(owner, cafeId, customer.getId());
        Long rewardId = rewards.get(0).getId();

        //when
        RewardUsedUpdateRequest request = new RewardUsedUpdateRequest(cafeId, true);
        리워드_사용(owner, request, customer.getId(), rewardId);
        List<RewardFindResponse> restRewards = 리워드_목록_조회(owner, cafeId, customer.getId());

        // then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(rewards.size()).isEqualTo(1);
        softAssertions.assertThat(restRewards.size()).isEqualTo(1);
        softAssertions.assertAll();
    }

    @Test
    void 카페사장이_가입_회원의_리워드를_조회한다() {
        // given
        Customer customer = 가입_회원_생성_후_가입_고객_반환();
        Owner owner = 카페_사장_생성_후_사장_반환();
        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("cafe", "잠실", "루터회관", "111111111");
        Long cafeId = 카페_생성_후_카페_아이디_반환(owner, cafeCreateRequest);
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(cafeId);
        Long couponId = 쿠폰_생성_후_쿠폰_아이디_반환(owner, couponCreateRequest, customer.getId());
        StampCreateRequest stampCreateRequest = new StampCreateRequest(10);
        스탬프_찍은_후_리워드_생성(owner, customer.getId(), couponId, stampCreateRequest);
        List<TemporaryCustomer> all = temporaryCustomerRepository.findAll();

        // when
        List<RewardFindResponse> rewards = 리워드_목록_조회(owner, cafeId, customer.getId());

        // then
        assertThat(rewards.size()).isEqualTo(1);
    }

    // TODO 회원가입, 로그인 구현 후 API CAll 로 대체
    private Customer 가입_회원_생성_후_가입_고객_반환() {
        return registerCustomerRepository.save(new RegisterCustomer("leo", "01022222222", "leoId", "5678"));
    }

    // TODO 회원가입, 로그인 구현 후 API CAll 로 대체
    private Customer 임시_회원_생성_후_가입_고객_반환() {
        return temporaryCustomerRepository.save(TemporaryCustomer.from("01011111111"));
    }

    // TODO 회원가입, 로그인 구현 후 API CAll 로 대체
    private Owner 카페_사장_생성_후_사장_반환() {
        return ownerRepository.save(new Owner("hardy", "hardyId", "1234", "01011111111"));
    }

    private Long 카페_생성_후_카페_아이디_반환(Owner owner, CafeCreateRequest cafeCreateRequest) {
        return Long.valueOf(
                given()
                        .contentType(JSON)
                        .body(cafeCreateRequest)
                        .auth().preemptive().basic(owner.getLoginId(), owner.getEncryptedPassword())
                        .when()
                        .post("/api/admin/cafes")
                        .thenReturn()
                        .header("Location")
                        .split("/")[2]);
    }

    private Long 쿠폰_생성_후_쿠폰_아이디_반환(Owner owner, CouponCreateRequest couponCreateRequest, Long customerId) {
        return given()
                .contentType(JSON)
                .body(couponCreateRequest)
                .auth().preemptive().basic(owner.getLoginId(), owner.getEncryptedPassword())
                .when()
                .post("/api/admin/customers/" + customerId + "/coupons")
                .thenReturn()
                .jsonPath()
                .getLong("couponId");
    }

    private void 스탬프_찍은_후_리워드_생성(Owner owner, Long customerId, Long couponId, StampCreateRequest stampCreateRequest) {
        given()
                .contentType(JSON)
                .body(stampCreateRequest)
                .auth().preemptive().basic(owner.getLoginId(), owner.getEncryptedPassword())
                .when()
                .post("/api/admin/customers/" + customerId + "/coupons/" + couponId + "/stamps");
    }

    private List<RewardFindResponse> 리워드_목록_조회(Owner owner, Long cafeId, Long customerId) {
        return given()
                .queryParam("cafe-id", cafeId)
                .queryParam("used", false)
                .contentType(JSON)
                .auth().preemptive().basic(owner.getLoginId(), owner.getEncryptedPassword())
                .when()
                .get("/api/admin/customers/" + customerId + "/rewards")
                .thenReturn()
                .jsonPath()
                .getList("rewards", RewardFindResponse.class);
    }

    private void 리워드_사용(Owner owner, RewardUsedUpdateRequest rewardUsedUpdateRequest, Long customerId, Long rewardId) {
        given()
                .contentType(JSON)
                .body(rewardUsedUpdateRequest)
                .auth().preemptive().basic(owner.getLoginId(), owner.getEncryptedPassword())
                .when()
                .patch("/api/admin/customers/" + customerId + "/rewards/" + rewardId);
    }
}
