package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.manager.reward.request.RewardUsedUpdateRequest;
import com.stampcrush.backend.api.manager.reward.response.RewardFindResponse;
import com.stampcrush.backend.api.visitor.favorites.request.FavoritesUpdateRequest;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.helper.BearerAuthHelper;
import com.stampcrush.backend.repository.user.CustomerRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
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
    private CustomerRepository customerRepository;

    @Test
    void 즐겨찾기를_등록한다() {
        // given
        Customer customer = 가입_회원_생성_후_가입_고객_반환();

        Owner owner = 카페_사장_생성_후_사장_반환();
        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("cafe", "잠실", "루터회관", "111111111");
        Long cafeId = 카페_생성_후_카페_아이디_반환(owner, cafeCreateRequest);
        FavoritesUpdateRequest request = new FavoritesUpdateRequest(Boolean.TRUE);

        // when
        ExtractableResponse<Response> response = 즐겨찾기_등록(request, customer, cafeId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    // TODO 회원가입, 로그인 구현 후 API CAll 로 대체
    private Customer 가입_회원_생성_후_가입_고객_반환() {
        Customer customer = Customer.registeredCustomerBuilder()
                .nickname("leo")
                .phoneNumber("01022222222")
                .loginId("leoId")
                .encryptedPassword("leoPw")
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

    private Long 카페_생성_후_카페_아이디_반환(Owner owner, CafeCreateRequest cafeCreateRequest) {
        return Long.valueOf(
                given()
                        .contentType(JSON)
                        .body(cafeCreateRequest)
                        .auth().preemptive()
                        .oauth2(BearerAuthHelper.generateToken(owner.getId()))
//                        .basic(owner.getLoginId(), owner.getEncryptedPassword())
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
                .auth().preemptive()
                .oauth2(BearerAuthHelper.generateToken(owner.getId()))
//                .basic(owner.getLoginId(), owner.getEncryptedPassword())
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
                .auth().preemptive()
                .oauth2(BearerAuthHelper.generateToken(owner.getId()))
//                .basic(owner.getLoginId(), owner.getEncryptedPassword())
                .when()
                .patch("/api/admin/customers/" + customerId + "/rewards/" + rewardId);
    }

    private ExtractableResponse<Response> 즐겨찾기_등록(FavoritesUpdateRequest favoritesUpdateRequest, Customer customer, Long cafeId) {
        return given()
                .contentType(JSON)
                .body(favoritesUpdateRequest)
                .auth().preemptive()
                .oauth2(BearerAuthHelper.generateToken(customer.getId()))
//                .basic(customer.getLoginId(), customer.getEncryptedPassword())
                .when()
                .post("/api/cafes/" + cafeId + "/favorites")
                .then()
                .extract();
    }
}
