package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.manager.reward.request.RewardUsedUpdateRequest;
import com.stampcrush.backend.api.manager.reward.response.RewardFindResponse;
import com.stampcrush.backend.api.visitor.favorites.request.FavoritesUpdateRequest;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.entity.user.RegisterCustomer;
import com.stampcrush.backend.entity.user.TemporaryCustomer;
import com.stampcrush.backend.repository.user.OwnerRepository;
import com.stampcrush.backend.repository.user.RegisterCustomerRepository;
import com.stampcrush.backend.repository.user.TemporaryCustomerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class VisitorFavoritesCommandAcceptanceTest extends AcceptanceTest {

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
    void 즐겨찾기를_등록한다() {
        // given
        RegisterCustomer customer = 가입_회원_생성_후_가입_고객_반환();
        Owner owner = 카페_사장_생성_후_사장_반환();
        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("cafe", "잠실", "루터회관", "111111111");
        Long cafeId = 카페_생성_후_카페_아이디_반환(owner, cafeCreateRequest);
        FavoritesUpdateRequest request = new FavoritesUpdateRequest(Boolean.TRUE);

        // when
        int statusCode = 즐겨찾기_등록(request, customer, cafeId)
                .statusCode();

        // then
        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());
    }

    // TODO 회원가입, 로그인 구현 후 API CAll 로 대체
    private RegisterCustomer 가입_회원_생성_후_가입_고객_반환() {
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

    private ExtractableResponse<Response> 즐겨찾기_등록(FavoritesUpdateRequest favoritesUpdateRequest, RegisterCustomer customer, Long cafeId) {
        return given()
                .contentType(JSON)
                .body(favoritesUpdateRequest)
                .auth().preemptive().basic(customer.getLoginId(), customer.getEncryptedPassword())
                .when()
                .post("/api/cafes/" + cafeId + "/favorites")
                .then()
                .extract();
    }
}
