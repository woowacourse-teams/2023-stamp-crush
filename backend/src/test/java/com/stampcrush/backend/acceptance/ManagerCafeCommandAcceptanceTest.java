package com.stampcrush.backend.acceptance;


import com.stampcrush.backend.api.manager.cafe.request.CafeCreateRequest;
import com.stampcrush.backend.api.manager.cafe.request.CafeUpdateRequest;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.user.OwnerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;

import static com.stampcrush.backend.acceptance.step.ManagerCafeCreateStep.카페_생성_요청하고_아이디_반환;
import static com.stampcrush.backend.acceptance.step.ManagerCafeUpdateStep.카페_정보_업데이트_요청;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ManagerCafeCommandAcceptanceTest extends AcceptanceTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void 카페_사장이_자기_카페_정보_업데이트() {
        // given
        Owner owner = ownerRepository.save(new Owner("jena", "jenaId", "jenaPw", "01012345678"));
        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("woowacafe", "road", "detail", "1234");
        CafeUpdateRequest cafeUpdateRequest = new CafeUpdateRequest("hi", LocalTime.MIDNIGHT, LocalTime.NOON, "010123421253", "cafeimage");

        Long cafeId = 카페_생성_요청하고_아이디_반환(owner, cafeCreateRequest);

        // when
        ExtractableResponse<Response> response = 카페_정보_업데이트_요청(owner, cafeUpdateRequest, cafeId);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    void 카페_사장이_자기_카페_아닌_카페_정보_업데이트하면_에러_발생() {
        // given
        Owner owner = ownerRepository.save(new Owner("jena", "jenaId", "jenaPw", "01012345678"));
        Owner notOwner = ownerRepository.save(new Owner("notOwner", "id", "pw", "1234567890"));

        CafeCreateRequest cafeCreateRequest = new CafeCreateRequest("woowacafe", "road", "detail", "1234");
        CafeUpdateRequest cafeUpdateRequest = new CafeUpdateRequest("hi", LocalTime.MIDNIGHT, LocalTime.NOON, "010123421253", "cafeimage");

        Long cafeId = 카페_생성_요청하고_아이디_반환(owner, cafeCreateRequest);

        // when
        ExtractableResponse<Response> response = 카페_정보_업데이트_요청(notOwner, cafeUpdateRequest, cafeId);

        // then
        assertThat(response.statusCode()).isEqualTo(401);
    }
}
