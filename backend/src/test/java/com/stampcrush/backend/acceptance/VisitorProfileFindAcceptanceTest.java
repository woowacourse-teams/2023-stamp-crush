package com.stampcrush.backend.acceptance;

import com.stampcrush.backend.api.visitor.profile.request.VisitorProfilesPhoneNumberUpdateRequest;
import com.stampcrush.backend.api.visitor.profile.response.VisitorProfilesFindByPhoneNumberResponse;
import com.stampcrush.backend.api.visitor.profile.response.VisitorProfilesFindResponse;
import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfileFindByPhoneNumberResultDto;
import com.stampcrush.backend.application.visitor.profile.dto.VisitorProfileFindResultDto;
import com.stampcrush.backend.entity.user.Customer;
import com.stampcrush.backend.entity.user.CustomerType;
import com.stampcrush.backend.helper.BearerAuthHelper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST;
import static com.stampcrush.backend.acceptance.step.VisitorJoinStep.가입_고객_회원_가입_요청하고_액세스_토큰_반환;
import static com.stampcrush.backend.acceptance.step.VisitorProfilesCommandStep.고객의_전화번호_등록_요청;
import static com.stampcrush.backend.acceptance.step.VisitorProfilesFindStep.고객의_프로필_조회_요청;
import static com.stampcrush.backend.acceptance.step.VisitorProfilesFindStep.전화번호로_고객의_프로필_조회_요청;
import static com.stampcrush.backend.fixture.CustomerFixture.REGISTER_CUSTOMER_GITCHAN;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.OK;

class VisitorProfileFindAcceptanceTest extends AcceptanceTest {

    @Test
    void 고객의_프로필을_조회한다() {
        // given
        String customerAccessToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST);

        // when
        ExtractableResponse<Response> response = 고객의_프로필_조회_요청(customerAccessToken);
        VisitorProfilesFindResponse expected = VisitorProfilesFindResponse.from(
                new VisitorProfileFindResultDto(1L,
                        REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST.getNickname(), null, REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST.getEmail()));
        VisitorProfilesFindResponse result = response.body().as(VisitorProfilesFindResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(OK.value()),
                () -> assertThat(result).usingRecursiveComparison().isEqualTo(expected)
        );
    }

    @Test
    void 전화번호로_고객의_프로필을_조회한다() {
        // given
        String customerAccessToken = 가입_고객_회원_가입_요청하고_액세스_토큰_반환(REGISTER_CUSTOMER_GITCHAN_CREATE_REQUEST);
        Long customerId = authTokensGenerator.extractMemberId(customerAccessToken);

        VisitorProfilesPhoneNumberUpdateRequest request = new VisitorProfilesPhoneNumberUpdateRequest("01012345678");
        고객의_전화번호_등록_요청(customerAccessToken, request);

        Customer customer = customerRepository.findById(customerId).get();

        // when
        ExtractableResponse<Response> response = 전화번호로_고객의_프로필_조회_요청(customerAccessToken, request.getPhoneNumber());

        VisitorProfilesFindByPhoneNumberResponse expected = VisitorProfilesFindByPhoneNumberResponse.from(
                new VisitorProfileFindByPhoneNumberResultDto(customerId,
                        customer.getNickname(), customer.getPhoneNumber(), CustomerType.REGISTER.name().toLowerCase()));
        VisitorProfilesFindByPhoneNumberResponse result = response.body().as(VisitorProfilesFindByPhoneNumberResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(OK.value()),
                () -> assertThat(result).usingRecursiveComparison().isEqualTo(expected)
        );
    }

    @Test
    void 없는_전화번호로_고객의_프로필을_조회하면_빈_배열을_반환한다() {
        // given
        Customer customer = customerRepository.save(REGISTER_CUSTOMER_GITCHAN);
        String wrongPhoneNumber = "7777777777";

        // when
        ExtractableResponse<Response> response = given()
                .log().all()
                .auth().preemptive()
                .oauth2(BearerAuthHelper.generateToken(customer.getId()))

                .when()
                .get("/api/profiles/search?phone-number=" + wrongPhoneNumber)

                .then()
                .log().all()
                .extract();

        VisitorProfilesFindByPhoneNumberResponse expected = VisitorProfilesFindByPhoneNumberResponse
                .from(null);

        VisitorProfilesFindByPhoneNumberResponse result = response.body().as(VisitorProfilesFindByPhoneNumberResponse.class);
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(OK.value()),
                () -> assertThat(result).usingRecursiveComparison().isEqualTo(expected)
        );
    }
}
