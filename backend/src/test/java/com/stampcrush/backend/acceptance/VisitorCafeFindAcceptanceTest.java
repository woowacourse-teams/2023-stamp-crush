package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.visitor.cafe.response.CafeInfoFindByCustomerResponse;
import com.stampcrush.backend.api.visitor.cafe.response.CafeInfoFindResponse;
import com.stampcrush.backend.application.visitor.cafe.dto.CafeInfoFindByCustomerResultDto;
import com.stampcrush.backend.auth.api.request.OAuthRegisterCustomerCreateRequest;
import com.stampcrush.backend.auth.api.request.OAuthRegisterOwnerCreateRequest;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.CAFE_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환_2;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.O_AUTH_OWNER_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.ManagerJoinStep.카페_사장_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorCafeFindStep.고객의_카페_정보_조회_요청;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.O_AUTH_REGISTER_CUSTOMER_CREATE_REQUEST_JENA;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class VisitorCafeFindAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CafeRepository cafeRepository;

    @Test
    void 고객이_카페정보를_조회한다() {
        // given
        OAuthRegisterCustomerCreateRequest registerCustomerCreateRequest = O_AUTH_REGISTER_CUSTOMER_CREATE_REQUEST_JENA;
        String accessTokenCustomer = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(registerCustomerCreateRequest);

        OAuthRegisterOwnerCreateRequest registerOwnerCreateRequest = O_AUTH_OWNER_CREATE_REQUEST;
        String accessTokenOwner = 카페_사장_회원_가입_요청하고_액세스_토큰_반환(registerOwnerCreateRequest);

        Long cafeId = 카페_생성_요청하고_아이디_반환_2(accessTokenOwner, CAFE_CREATE_REQUEST);
        Cafe cafe = cafeRepository.findById(cafeId).get();

        // when
        ExtractableResponse<Response> response = 고객의_카페_정보_조회_요청(accessTokenCustomer, cafeId);
        CafeInfoFindByCustomerResponse cafeInfoFindByCustomerResponse = response.body().as(CafeInfoFindByCustomerResponse.class);

        // then
        assertThat(cafeInfoFindByCustomerResponse.getCafe())
                .usingRecursiveComparison()
                .isEqualTo(CafeInfoFindResponse.from(CafeInfoFindByCustomerResultDto.from(cafe)));
    }

    @Test
    void 고객이_존재하지_않는_카페를_조회하면_에러가_발생한다() {
        OAuthRegisterCustomerCreateRequest registerCustomerCreateRequest = O_AUTH_REGISTER_CUSTOMER_CREATE_REQUEST_JENA;
        String accessTokenCustomer = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(registerCustomerCreateRequest);

        long NOT_EXIST_CAFE_ID = 1L;
        ExtractableResponse<Response> response = 고객의_카페_정보_조회_요청(accessTokenCustomer, NOT_EXIST_CAFE_ID);

        assertThat(response.statusCode()).isEqualTo(NOT_FOUND.value());
    }
}
