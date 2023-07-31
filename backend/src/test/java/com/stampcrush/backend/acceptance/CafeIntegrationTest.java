package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.cafe.response.CafeInfoFindByCustomerResponse;
import com.stampcrush.backend.api.cafe.response.CafeInfoFindResponse;
import com.stampcrush.backend.application.cafe.dto.CafeInfoFindByCustomerResultDto;
import com.stampcrush.backend.entity.cafe.Cafe;
import com.stampcrush.backend.entity.user.Owner;
import com.stampcrush.backend.repository.cafe.CafeRepository;
import com.stampcrush.backend.repository.user.OwnerRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class CafeIntegrationTest extends AcceptanceTest {

    private static final int NOT_EXIST_CAFE_ID = 1;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void 고객이_카페정보를_조회한다() {
        // given
        Cafe savedCafe = cafeRepository.save(new Cafe(
                "깃짱카페",
                LocalTime.NOON,
                LocalTime.MIDNIGHT,
                "01012345678",
                "#",
                "서울시 올림픽로 어쩌고",
                "루터회관",
                "10-222-333",
                ownerRepository.save(
                        new Owner(
                                "깃짱",
                                "깃짱아이디",
                                "깃짱비밀번호",
                                "깃짱핸드폰번호")
                )));

        // when
        ExtractableResponse<Response> response = requestFindCafeByCustomer(savedCafe.getId());
        CafeInfoFindByCustomerResponse cafeInfoFindByCustomerResponse = response.body().as(CafeInfoFindByCustomerResponse.class);

        // then
        assertThat(cafeInfoFindByCustomerResponse.getCafe()).usingRecursiveComparison()
                .isEqualTo(CafeInfoFindResponse.from(CafeInfoFindByCustomerResultDto.from(savedCafe)));
    }

    @Test
    void 고객이_존재하지_않는_카페를_조회하면_에러가_발생한다() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/cafes/" + NOT_EXIST_CAFE_ID)
                .then()
                .statusCode(NOT_FOUND.value())
                .extract();
    }

    private ExtractableResponse<Response> requestFindCafeByCustomer(Long cafeId) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/cafes/" + cafeId)
                .then()
                .log().all()
                .extract();
    }
}
