package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.visitor.favorites.request.FavoritesUpdateRequest;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.favorites.Favorites;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.repository.favorites.FavoritesRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.OWNER_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorFavoritesCommandStep.즐겨찾기_등록;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

class VisitorFavoritesCommandAcceptanceTest extends AcceptanceTest {

    @Autowired
    private FavoritesRepository favoritesRepository;

    @Test
    void 즐겨찾기를_등록한다() {
        // given
        String ownerAccessToken = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(OWNER_CREATE_REQUEST);
        String customerAccessToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST);
        Long customerId = authTokensGenerator.extractMemberId(customerAccessToken);
        Customer customer = customerRepository.findById(customerId).get();

        Long cafeId = 카페_생성_요청하고_아이디_반환(ownerAccessToken, CAFE_CREATE_REQUEST);
        Cafe cafe = cafeRepository.findById(cafeId).get();

        FavoritesUpdateRequest request = new FavoritesUpdateRequest(Boolean.TRUE);


        // when
        ExtractableResponse<Response> response = 즐겨찾기_등록(customerAccessToken, request, cafeId);
        Favorites favorite = favoritesRepository.findByCafeAndCustomer(cafe, customer).get();

        // then
        assertThat(response.statusCode()).isEqualTo(OK.value());
        assertThat(favorite.getIsFavorites()).isTrue();
    }
}
